package me.pljr.killstreak.commands;

import me.pljr.killstreak.config.CfgLang;
import me.pljr.killstreak.enums.Lang;
import me.pljr.killstreak.menus.KillStreakMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillStreakCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(CfgLang.lang.get(Lang.NO_CONSOLE));
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("killstreak.use")){
            player.sendMessage(CfgLang.lang.get(Lang.NO_PERM));
            return false;
        }
        player.openInventory(KillStreakMenu.getMenu(player));
        return true;
    }
}
