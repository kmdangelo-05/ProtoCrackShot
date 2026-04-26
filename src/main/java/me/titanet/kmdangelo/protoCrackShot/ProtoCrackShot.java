package me.titanet.kmdangelo.protoCrackShot;

import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import java.util.HashMap;
import java.util.HashSet;

public final class ProtoCrackShot extends JavaPlugin {

    Map<String, Weapon> weaponCatalog = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        ConfigurationSection weaponsSection = getConfig().getConfigurationSection("Weapons");

        for (String weaponID : weaponsSection.getKeys(false)) {
            ConfigurationSection wData = getConfig().getConfigurationSection(weaponID);

            if (wData == null) return;

            String name = wData.getString("name", "&4&ldefault name");
            int maxAmmo = wData.getInt("max_ammo", 0);
            boolean infinteAmmo = wData.getBoolean("infinite_ammo", false);
            int reloadTimeInSeconds = wData.getInt("reload_time", 0);
            boolean hitScan = wData.getBoolean("is_hitscan", false);
            Material itemType = Material.matchMaterial(wData.getString("item_type", "BARRIER"));
            EntityType projectileType = EntityType.valueOf(wData.getString("projectile_type", null));
            Particle particleType = Particle.valueOf(wData.getString("particle", null));
            Effect effectInflicted = Effect.valueOf(wData.getString("effect_inflicted", null));
            double range = wData.getDouble("range", 0);
            double damage = wData.getDouble("damage", 0);
            double hitsPerSecond = wData.getDouble("hits_per_second", 0);

            Weapon weapon = new Weapon(this, weaponID, name, maxAmmo, infinteAmmo, reloadTimeInSeconds, hitScan, itemType, projectileType, particleType, effectInflicted, range, damage, hitsPerSecond);
            weaponCatalog.putIfAbsent(weaponID, weapon);
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }

    public Map<String, Weapon> getWeaponCatalog() {
        return weaponCatalog;
    }

}
