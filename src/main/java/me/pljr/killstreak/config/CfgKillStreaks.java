package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.ConfigManager;
import me.pljr.killstreak.managers.KillStreakManager;

import java.util.HashMap;
import java.util.List;

public class CfgKillStreaks {
    private static final ConfigManager mngr = KillStreak.getConfigManager();

    public static HashMap<Integer, KillStreakManager> killstreaks = new HashMap<>();

    public static void load(){
        List<KillStreakManager> unsortedKillStreaks = mngr.getKillStreaks("killstreaks");
        for (KillStreakManager killstreak : unsortedKillStreaks){
            int start = killstreak.getStart();
            int end = killstreak.getEnd();
            for (int i=start;i<=end;i++){
                killstreaks.put(i, killstreak);
            }
        }
    }
}
