package me.pljr.killstreak.commands;

import me.pljr.killstreak.killstreak.KillStreakManager;
import me.pljr.killstreak.menus.KillStreakMenu;
import me.pljr.pljrapispigot.commands.BukkitCommand;
import org.bukkit.entity.Player;

public class KillStreakCommand extends BukkitCommand {

    private final KillStreakManager streakManager;

    public KillStreakCommand(KillStreakManager streakManager){
        super("killstreak", "killstreak.use");
        this.streakManager = streakManager;
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        new KillStreakMenu(player, streakManager).getGui().open(player);
    }
}
