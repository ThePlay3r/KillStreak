package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CfgMenu {
    private final static ConfigManager config = KillStreak.getConfigManager();

    public static String top1Name;
    public static List<String> top1Lore;
    public static String top2Name;
    public static List<String> top2Lore;
    public static String top3Name;
    public static List<String> top3Lore;
    public static ItemStack update;
    public static ItemStack leaderboard;

    public static void load(){
        CfgMenu.top1Name = config.getString("menu.top1.name");
        CfgMenu.top1Lore = config.getStringList("menu.top1.lore");
        CfgMenu.top2Name = config.getString("menu.top2.name");
        CfgMenu.top2Lore = config.getStringList("menu.top2.lore");
        CfgMenu.top3Name = config.getString("menu.top3.name");
        CfgMenu.top3Lore = config.getStringList("menu.top3.lore");
        CfgMenu.update = config.getSimpleItemStack("menu.update");
        CfgMenu.leaderboard = config.getSimpleItemStack("menu.leaderboard");
    }
}
