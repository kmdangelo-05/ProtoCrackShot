package me.titanet.kmdangelo.protoCrackShot.weaponLogic;

import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

public class Weapon {
    private ProtoCrackShot plugin;

    private String weaponID;

    private String name;

    private int maxAmmo;
    private boolean infiniteAmmo;
    private int reloadTimeInSeconds;

    private boolean hitScan;

    private Material itemType;
    private EntityType projectileType;
    private Particle particleType;

    private Effect inflictedEffectOnHit;
    private double range;
    private double damage;
    private double hitsPerSecond;

    private boolean reloading;
    private String nameWhileReloading;

    public Weapon(ProtoCrackShot plugin, String weaponID, String name, int maxAmmo, boolean infiniteAmmo, int reloadTimeInSeconds, boolean hitScan, Material itemType, EntityType projectileType, Particle particleType, Effect inflictedEffectOnHit, double range, double damage, double hitsPerSecond) {
        this.plugin = plugin;
        this.weaponID = weaponID;
        this.name = name;
        this.maxAmmo = maxAmmo;
        this.infiniteAmmo = infiniteAmmo;
        this.reloadTimeInSeconds = reloadTimeInSeconds;
        this.hitScan = hitScan;
        this.itemType = itemType;
        this.projectileType = projectileType;
        this.particleType = particleType;
        this.inflictedEffectOnHit = inflictedEffectOnHit;
        this.range = range;
        this.damage = damage;
        this.hitsPerSecond = hitsPerSecond;
        this.reloading = false;
        this.nameWhileReloading = name + " ®";
    }

    public ProtoCrackShot getPlugin() {
        return plugin;
    }

    public void setPlugin(ProtoCrackShot plugin) {
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public boolean isInfiniteAmmo() {
        return infiniteAmmo;
    }

    public void setInfiniteAmmo(boolean infiniteAmmo) {
        this.infiniteAmmo = infiniteAmmo;
    }

    public String getWeaponID() {
        return weaponID;
    }

    public void setWeaponID(String weaponID) {
        this.weaponID = weaponID;
    }

    public int getReloadTimeInSeconds() {
        return reloadTimeInSeconds;
    }

    public void setReloadTimeInSeconds(int reloadTimeInSeconds) {
        this.reloadTimeInSeconds = reloadTimeInSeconds;
    }

    public boolean isHitScan() {
        return hitScan;
    }

    public void setHitScan(boolean hitScan) {
        this.hitScan = hitScan;
    }

    public EntityType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(EntityType projectileType) {
        this.projectileType = projectileType;
    }

    public Particle getParticleType() {
        return particleType;
    }

    public void setParticleType(Particle particleType) {
        this.particleType = particleType;
    }

    public Effect getInflictedEffectOnHit() {
        return inflictedEffectOnHit;
    }

    public void setInflictedEffectOnHit(Effect inflictedEffectOnHit) {
        this.inflictedEffectOnHit = inflictedEffectOnHit;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public String getNameWhileReloading() {
        return nameWhileReloading;
    }

    public void setNameWhileReloading(String nameWhileReloading) {
        this.nameWhileReloading = nameWhileReloading;
    }
}
