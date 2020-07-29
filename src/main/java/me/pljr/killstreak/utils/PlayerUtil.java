package me.pljr.killstreak.utils;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.database.QueryManager;
import me.pljr.killstreak.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerUtil {
    private static HashMap<String, PlayerManager> players = new HashMap<>();
    public static final QueryManager query = KillStreak.getQueryManager();

    public static PlayerManager getPlayerManager(String pName){
        if (players.containsKey(pName)){
            return players.get(pName);
        }
        query.loadPlayerSync(pName);
        return getPlayerManager(pName);
    }

    public static void setPlayerManager(String pName, PlayerManager playerManager){
        players.put(pName, playerManager);
    }

    public static boolean isPlayer(String name){
        Player player = Bukkit.getPlayer(name);
        return player != null && player.isOnline();
    }
}
