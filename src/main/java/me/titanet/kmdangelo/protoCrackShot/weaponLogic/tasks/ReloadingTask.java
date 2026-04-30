package me.titanet.kmdangelo.protoCrackShot.weaponLogic.tasks;

import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ReloadingTask extends BukkitRunnable {

    private double timeInMillis;

    private ItemStack weapon;
    private UUID uuid;
    private ProtoCrackShot plugin;
    private Player player;
    private Weapon weaponType;

    public ReloadingTask(ProtoCrackShot plugin, ItemStack weapon, UUID uuid) {
        this.plugin = plugin;
        this.weapon = weapon;
        player = Bukkit.getPlayer(uuid);
        weaponType = plugin.getWeaponCatalog().get(weapon.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin,"weapon_type"), PersistentDataType.STRING));
        timeInMillis = System.currentTimeMillis() + weaponType.getReloadTimeInMillis()*1000L;
    }

    @Override
    public void run() {
        NamespacedKey ammoNK = new NamespacedKey(plugin, "ammo_amount");
        double remainingTimeInMillis = timeInMillis - System.currentTimeMillis();
        weapon.getItemMeta().setItemName(weaponType.getNameWhileReloading());
        if(remainingTimeInMillis>0) {
            int fraction = (int) Math.round(remainingTimeInMillis/(weaponType.getReloadTimeInMillis()/5));   // 20 sec tdr: 20/5=4  12/4 = 3
            String reloadBar;
            switch (fraction) {
                case 4:
                    reloadBar = "&f&l[&e|&f||||]";
                    break;
                case 3:
                    reloadBar = "&f&l[&e||&f|||]";
                    break;
                case 2:
                    reloadBar = "&f&l[&e|||&f||]";
                    break;
                case 1:
                    reloadBar = "&f&l[&e||||&f|]";
                    break;
                case 0:
                    reloadBar = "&f&l[&e|||||&f]";
                    break;
                default:
                    reloadBar = "&f&l[|||||]";
                    break;
            }
            String rawText = ChatColor.translateAlternateColorCodes('&',"&l&eRELOADING "+reloadBar);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(rawText));
        } else {
            weapon.getItemMeta().setItemName(weaponType.getName());
            weapon.getItemMeta().setLore();
            weapon.getItemMeta().getPersistentDataContainer().set(ammoNK, PersistentDataType.INTEGER, weaponType.getMaxAmmo());
            plugin.getItemReloadings().remove(uuid);
            this.cancel();
        }

    }
}
