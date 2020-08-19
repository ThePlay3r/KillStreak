package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.pljrapi.managers.ConfigManager;

public class CfgSettings {
    private static final ConfigManager config = KillStreak.getConfigManager();

    public static boolean bungee;
    public static int leaderboard;
    public static int autoupdate;

    public static void load(){
        CfgSettings.bungee = config.getBoolean("settings.bungee");
        CfgSettings.leaderboard = config.getInt("settings.leaderboard");
        CfgSettings.autoupdate = config.getInt("settings.autoupdate");
    }
}
