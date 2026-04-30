package me.titanet.kmdangelo.protoCrackShot;

import lombok.Data;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

@Data
public class Config{
    private ProtoCrackShot plugin;

    public static void configureConfig (ProtoCrackShot plugin) {
        ConfigurationSection weaponsSection = plugin.getConfig().getConfigurationSection("Weapons");

        for (String weaponID : weaponsSection.getKeys(false)) {
            ConfigurationSection wData = plugin.getConfig().getConfigurationSection(weaponID);

            if (wData == null) return;

            String name = wData.getString("name", "&4&ldefault name");
            List<String> lore = wData.getStringList("lore");
            int maxAmmo = wData.getInt("max_ammo", 0);
            BarColor ammoBarColor = switch(wData.getString("ammo_bar_color","PURPLE").toUpperCase()) {
                case "YELLOW" -> BarColor.YELLOW;
                case "WHITE" -> BarColor.WHITE;
                case "PINK" -> BarColor.PINK;
                case "BLUE" -> BarColor.BLUE;
                case "GREEN" -> BarColor.GREEN;
                case "RED" -> BarColor.RED;
                default -> BarColor.PURPLE;
            };

            boolean infinteAmmo = wData.getBoolean("infinite_ammo", false);
            int reloadTimeInSeconds = wData.getInt("reload_time", 0);
            boolean hitScan = wData.getBoolean("is_hitscan", false);
            Material itemType = Material.matchMaterial(wData.getString("item_type", "BARRIER"));

            Class<? extends Projectile> projectileType = switch (wData.getString("projectile_type", null)) {
                case "SNOWBALL" -> Snowball.class;
                case "ARROW" -> Arrow.class;
                case "FIREBALL" -> Fireball.class;
                case "WITHER_SCULL" -> WitherSkull.class;
                case "EGG" -> Egg.class;
                default -> Arrow.class;
            };

            Particle particleType = Particle.valueOf(wData.getString("particle", null));
            int particleAmount = wData.getInt("particle_amount",0);
            PotionEffect effectInflicted = new PotionEffect(PotionEffectType.getByName(wData.getString("effect_inflicted")), wData.getInt("effect_duration",0), wData.getInt("effect_intensity",1));
            Sound sound = Sound.valueOf(wData.getString("sound",null));
            double soundVolume = wData.getDouble("sound_volume", 0);
            double soundPitch = wData.getDouble("sound_pitch", 0);
            double range = wData.getDouble("range", 0);
            double damage = wData.getDouble("damage", 0);
            double hitsPerSecond = wData.getDouble("hits_per_second", 1);
            double precision = wData.getDouble("precision", 1);
            boolean hasShiftZoom = wData.getBoolean("has_shift_zoom", false);
            double zoom = wData.getDouble("zoom",0);
            double headshotMultiplayer = wData.getDouble("headshot_multiplayer",1);
            double recoil = wData.getDouble("recoil", 0);
            boolean rightClickShot = wData.getBoolean("right_click_1shot", false);
            boolean isConsumable = wData.getBoolean("is_consumable", false);

            Weapon weapon = new Weapon(
                    plugin, weaponID, name, lore,
                    maxAmmo, ammoBarColor, infinteAmmo, reloadTimeInSeconds,
                    hitScan, itemType, projectileType,
                    particleType, particleAmount, effectInflicted, sound,
                    soundVolume, soundPitch, range,
                    damage, hitsPerSecond, precision,
                    hasShiftZoom, zoom, headshotMultiplayer,
                    recoil, rightClickShot, isConsumable
            );
            plugin.weaponCatalog.putIfAbsent(weaponID, weapon);
        }
    }
}
