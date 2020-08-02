package me.pljr.killstreak.config;

import me.pljr.killstreak.objects.KillStreak;
import me.pljr.pljrapi.managers.ConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CfgKillStreaks {
    private static final ConfigManager mngr = me.pljr.killstreak.KillStreak.getConfigManager();

    public static HashMap<Integer, KillStreak> killstreaks = new HashMap<>();

    public static void load(){
        List<KillStreak> unsortedKillStreaks = new ArrayList<>();
        for (String killstreak : mngr.getConfigurationSection("killstreaks").getKeys(false)){
            unsortedKillStreaks.add(new KillStreak(
                    mngr.getInt("killstreaks."+killstreak+".start"),
                    mngr.getInt("killstreaks."+killstreak+".end"),
                    mngr.getBoolean("killstreaks."+killstreak+".broadcast"),
                    mngr.getStringList("killstreaks."+killstreak+".broadcast-message"),
                    mngr.getPLJRTitle("killstreaks."+killstreak+".title"),
                    mngr.getPLJRSound("killstreaks."+killstreak+".sound")
            ));
        }
        for (KillStreak killstreak : unsortedKillStreaks){
            int start = killstreak.getStart();
            int end = killstreak.getEnd();
            for (int i=start;i<=end;i++){
                killstreaks.put(i, killstreak);
            }
        }
    }
}
