package me.titanet.kmdangelo.protoCrackShot.listeners;

import lombok.Getter;
import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.tasks.ReloadingTask;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.tasks.ShootingTask;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.UUID;

@Getter
public class WeaponInteractionListener implements Listener {

    private ProtoCrackShot plugin;

    public WeaponInteractionListener(ProtoCrackShot plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PlayerRechargeWeaponEvent(PlayerDropItemEvent e) {
        UUID playerUUID = e.getPlayer().getUniqueId();
        ItemStack mainHandItem = e.getPlayer().getInventory().getItemInMainHand();
        if (Objects.requireNonNull(mainHandItem.getItemMeta()).getPersistentDataContainer().getKeys().contains(new NamespacedKey(plugin,"weapon_type"))) {
            e.setCancelled(true);
            plugin.getItemReloadings().put(playerUUID, new ReloadingTask(plugin, mainHandItem, playerUUID));
            plugin.getItemReloadings().get(playerUUID).runTaskTimer(plugin,0L,20L);

        }
    }

    @EventHandler
    public void PlayerUseWeaponEvent(PlayerInteractEvent e) {
        NamespacedKey weaponTypeNK = new NamespacedKey(plugin,"weapon_type");

        UUID playerUUID = e.getPlayer().getUniqueId();
        ItemStack mainHandItem = e.getPlayer().getInventory().getItemInMainHand();
        Weapon weaponItem = plugin.getWeaponCatalog().get(mainHandItem.getItemMeta().getPersistentDataContainer().get(weaponTypeNK, PersistentDataType.STRING));

        ShootingTask shootingT = new ShootingTask(plugin, mainHandItem, playerUUID);

        if (!mainHandItem.hasItemMeta()) return;
        if (mainHandItem.getItemMeta().getPersistentDataContainer().getKeys().contains(new NamespacedKey(plugin,"weapon_type"))) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && weaponItem.isRightClickShot()) {
                shootingT.run();
            } else if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && !weaponItem.isRightClickShot()) {
                shootingT.run();
            }
        }
    }

    @EventHandler
    public void PlayerWeaponShiftEvent(PlayerToggleSneakEvent e) {
        NamespacedKey weaponTypeNK = new NamespacedKey(plugin,"weapon_type");
        ItemStack mainHandItem = e.getPlayer().getInventory().getItemInMainHand();
        Weapon weaponItem = plugin.getWeaponCatalog().get(mainHandItem.getItemMeta().getPersistentDataContainer().get(weaponTypeNK, PersistentDataType.STRING));


        if (mainHandItem.getItemMeta().getPersistentDataContainer().getKeys().contains(weaponTypeNK)) {
            if (!weaponItem.isHasShiftZoom()) return;
            if (e.getPlayer().isSneaking()) {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (int) weaponItem.getZoom(), Integer.MAX_VALUE));
            } else {
                e.getPlayer().removePotionEffect(PotionEffectType.SLOWNESS);
            }
        }
    }
}
