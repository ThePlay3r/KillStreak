package me.pljr.killstreak.config;

import lombok.Getter;
import me.pljr.pljrapispigot.managers.ConfigManager;

@Getter
public class Settings {
    private static final String PATH = "settings";

    private final boolean cachePlayers;
    private final boolean bungee;
    private final int leaderboard;
    private final int autoUpdate;

    public Settings(ConfigManager config){
        cachePlayers = config.getBoolean(PATH+".cache-players");
        bungee = config.getBoolean(PATH+".bungee");
        leaderboard = config.getInt(PATH+".leaderboard");
        autoUpdate = config.getInt(PATH+".autoupdate");
    }
}
