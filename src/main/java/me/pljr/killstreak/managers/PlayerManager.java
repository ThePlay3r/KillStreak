package me.pljr.killstreak.managers;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.objects.CorePlayer;

import java.util.HashMap;

public class PlayerManager {
    private static final HashMap<String, CorePlayer> players = new HashMap<>();
    private static final QueryManager query = KillStreak.getQueryManager();

    public static CorePlayer getCorePlayer(String pName){
        if (players.containsKey(pName)){
            return players.get(pName);
        }
        query.loadPlayerSync(pName);
        return getCorePlayer(pName);
    }

    public static void setCorePlayer(String pName, CorePlayer corePlayer){
        players.put(pName, corePlayer);
    }
}
