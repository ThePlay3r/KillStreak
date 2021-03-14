package me.pljr.killstreak.config;

import lombok.Getter;
import me.pljr.killstreak.killstreak.KillStreak;
import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class KillStreaks {
    private final static String PATH = "killstreaks";

    private final HashMap<Integer, KillStreak> killstreaks;

    public KillStreaks(ConfigManager config){
        killstreaks = new HashMap<>();
        List<KillStreak> unsortedKillStreaks = new ArrayList<>();
        ConfigurationSection cs = config.getConfigurationSection(PATH);
        if (cs != null){
            for (String killstreak : cs.getKeys(false)){
                unsortedKillStreaks.add(new KillStreak(
                        config.getInt(PATH+"."+killstreak+".start"),
                        config.getInt(PATH+"."+killstreak+".end"),
                        config.getBoolean(PATH+"."+killstreak+".broadcast"),
                        config.getStringList(PATH+"."+killstreak+".broadcast-message"),
                        config.getPLJRTitle(PATH+"."+killstreak+".title"),
                        config.getPLJRSound(PATH+"."+killstreak+".sound")
                ));
            }
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
