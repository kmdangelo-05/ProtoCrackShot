package me.titanet.kmdangelo.protoCrackShot.weaponLogic;

import lombok.Getter;
import lombok.Setter;

import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter @Setter
public class Weapon {

    private ProtoCrackShot plugin;

    private String weaponID;

    private String name;
    private List<String> lore;

    private int maxAmmo;    // 0 means the weapon consumes on usage
    private BarColor ammoBarColor;
    private boolean infiniteAmmo;  //infinite ammo means Integer max value magazine size and no reloading time
    private double reloadTimeInMillis;

    private boolean hitScan;

    private Material itemType;
    private Class<? extends Projectile> projectileType;

    private Particle particleType;
    private int particleAmount;

    private PotionEffect inflictedEffectOnHit;

    private Sound sound;
    private double soundVolume;
    private double soundPitch;

    private double range;
    private double damage;
    private double hitsPerSecond;
    private double precision; //0 minimum precision, 1 max precision

    private boolean hasShiftZoom;
    private double zoom;
    private double headshotMultiplayer;
    private double recoil;

    private boolean rightClickShot;

    private String nameWhileReloading;

    public Weapon(
            ProtoCrackShot plugin, String weaponID, String name, List<String> lore,
            int maxAmmo, BarColor ammoBarColor, boolean infiniteAmmo, int reloadTimeInSeconds,
            boolean hitScan, Material itemType, Class<? extends Projectile> projectileType,
            Particle particleType, int particleAmount,
            PotionEffect inflictedEffectOnHit, Sound sound,
            double soundVolume, double soundPitch, double range,
            double damage, double hitsPerSecond, double precision,
            boolean hasShiftZoom, double zoom, double headshotMultiplayer,
            double recoil, boolean rightClickShot, boolean isConsumable
    ) {
        this.plugin = plugin;
        this.weaponID = weaponID;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.lore = new ArrayList<>();
        if (!isConsumable) {
            this.infiniteAmmo = infiniteAmmo;
            if (infiniteAmmo) {
                this.maxAmmo = Integer.MAX_VALUE;
                this.reloadTimeInMillis = 0;
            } else {
                this.maxAmmo = maxAmmo;
                this.reloadTimeInMillis = reloadTimeInSeconds;
            }
        } else {
            this.infiniteAmmo = false;
            this.maxAmmo = 0;
            this.reloadTimeInMillis = 0;
        }
        this.ammoBarColor = ammoBarColor;
        this.hitScan = hitScan;
        this.itemType = itemType;
        this.projectileType = projectileType;
        this.particleType = particleType;
        this.particleAmount = particleAmount;
        this.inflictedEffectOnHit = inflictedEffectOnHit;
        this.sound = sound;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
        this.range = range;
        this.damage = damage;

        if (0 <= precision && precision <= 1) {
            this.precision = precision;
        } else if (precision < 0) {
            this.precision = 0;
        } else {
            this.precision = 1;
        }

        this.hitsPerSecond = hitsPerSecond;
        this.hasShiftZoom = hasShiftZoom;

        if (hasShiftZoom) {
            this.zoom = zoom;
        } else {
            this.zoom = 0;
        }

        this.headshotMultiplayer = headshotMultiplayer;

        this.recoil = recoil;

        this.rightClickShot = rightClickShot;

        this.nameWhileReloading = name + " ®";
    }

    //Methods

    public ItemStack generateNewWeapon() {
        ItemStack weaponItem = new ItemStack(itemType);

        ItemMeta meta = weaponItem.getItemMeta();
        if (meta == null) return null;

        meta.setItemName(name);
        if (!lore.isEmpty()) meta.setLore(lore);

        NamespacedKey wUuid = new NamespacedKey(plugin, "weapon_unique_id");
        meta.getPersistentDataContainer().set(wUuid, PersistentDataType.STRING, UUID.randomUUID().toString());

        NamespacedKey weaponType = new NamespacedKey(plugin, "weapon_type");
        meta.getPersistentDataContainer().set(weaponType, PersistentDataType.STRING, this.weaponID);

        NamespacedKey weaponAmmoAmount = new NamespacedKey(plugin, "ammo_amount");
        meta.getPersistentDataContainer().set(weaponAmmoAmount, PersistentDataType.INTEGER, this.maxAmmo);

        weaponItem.setItemMeta(meta);



        return weaponItem;
    }

}

