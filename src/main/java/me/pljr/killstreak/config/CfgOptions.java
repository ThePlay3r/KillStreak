package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.ConfigManager;

public class CfgOptions {
    private static final ConfigManager config = KillStreak.getConfigManager();

    public static int leaderboard;
    public static int autoupdate;
    public static boolean bungee;

    public static void load(){
        CfgOptions.leaderboard = config.getInt("options.leaderboard");
        CfgOptions.autoupdate = config.getInt("options.autoupdate");
        CfgOptions.bungee = config.getBoolean("options.bungee");
    }
}
