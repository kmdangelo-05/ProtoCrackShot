package me.titanet.kmdangelo.protoCrackShot.weaponLogic.tasks;

import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.UUID;

public class ShootingTask extends BukkitRunnable {
    private ProtoCrackShot plugin;

    private ItemStack weapon;
    private UUID uuid;
    private Player player;
    private Weapon weaponType;


    public ShootingTask(ProtoCrackShot plugin, ItemStack weapon, UUID uuid) {
        this.plugin = plugin;
        this.weapon = weapon;
        player = Bukkit.getPlayer(uuid);
        weaponType = plugin.getWeaponCatalog().get(weapon.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin,"weaponID"), PersistentDataType.STRING));
    }

    @Override
    public void run() {
        NamespacedKey ammoNK = new NamespacedKey(plugin, "ammo_amount");
        int remainingAmmo;

        try {
            remainingAmmo = weapon.getItemMeta().getPersistentDataContainer().get(ammoNK,PersistentDataType.INTEGER);
        } catch (NullPointerException e) {
            player.sendMessage("Errore Crackshot: quantità di proiettili nulla");
            return;
        }

        if (remainingAmmo <= 0) return;

        Location playerEyeLocation = player.getEyeLocation();
        Vector playerSightDirection = playerEyeLocation.getDirection();
        Location frontLocation = playerEyeLocation.add(playerSightDirection.multiply(2));
        player.playSound(player.getLocation(), weaponType.getSound(), (float) weaponType.getSoundVolume(), (float) weaponType.getSoundPitch());
        player.spawnParticle(weaponType.getParticleType(), frontLocation, weaponType.getParticleAmount());

        if (remainingAmmo<=0) return;

        if (!weaponType.isInfiniteAmmo()) {
            weapon.getItemMeta().getPersistentDataContainer().set(ammoNK, PersistentDataType.INTEGER, remainingAmmo-1);
        }

        if(weaponType.getRecoil() != 0) {
            player.setVelocity(playerSightDirection.multiply(-weaponType.getRecoil()));
        }

        if (weaponType.isHitScan()) {
            RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(playerEyeLocation, playerSightDirection, weaponType.getRange(), entity -> !entity.equals(player));
            Entity hitEntity;
            try {
                hitEntity = rayTraceResult.getHitEntity();
            } catch (NullPointerException e) {
                return;
            }
            if (!(hitEntity instanceof LivingEntity)) return;
            LivingEntity victim = (LivingEntity) hitEntity;
            victim.damage(weaponType.getDamage(), player);
            victim.addPotionEffect(weaponType.getInflictedEffectOnHit());
        } else {
            return;
        }

    }
}
