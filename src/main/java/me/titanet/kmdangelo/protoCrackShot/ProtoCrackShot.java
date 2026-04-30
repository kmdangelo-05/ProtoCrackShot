package me.titanet.kmdangelo.protoCrackShot;

import lombok.Getter;
import me.titanet.kmdangelo.protoCrackShot.listeners.WeaponInteractionListener;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.AmmoBossBar;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.Weapon;
import me.titanet.kmdangelo.protoCrackShot.weaponLogic.tasks.ReloadingTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public final class ProtoCrackShot extends JavaPlugin {

    Map<String, Weapon> weaponCatalog = new HashMap<>();
    Map<UUID, ReloadingTask> itemReloadings = new HashMap<>();
    Map<UUID, AmmoBossBar> ammoBars = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new WeaponInteractionListener(this),this);

        Config.configureConfig(this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }
}
