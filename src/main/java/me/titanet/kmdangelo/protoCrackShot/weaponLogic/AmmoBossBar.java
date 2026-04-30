package me.titanet.kmdangelo.protoCrackShot.weaponLogic;

import lombok.Data;
import me.titanet.kmdangelo.protoCrackShot.ProtoCrackShot;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Data
public class AmmoBossBar implements BossBar{
    ProtoCrackShot plugin;

    String title;
    BarColor color;
    BarStyle style;
    BarFlag flag;
    boolean visible;
    double progress;
    List<Player> players;

    @Override
    public void removeFlag(@NotNull BarFlag flag) {
    }

    @Override
    public void addFlag(@NotNull BarFlag flag) {
    }

    @Override
    public boolean hasFlag(@NotNull BarFlag flag) {
        return false;
    }

    @Override
    public void addPlayer(@NotNull Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer(@NotNull Player player) {
        players.remove(player);
    }

    @Override
    public void removeAll() {
        players.clear();
    }

    @Override
    public void show() {
        this.setVisible(true);
    }

    @Override
    public void hide() {
        this.setVisible(false);
    }
}
