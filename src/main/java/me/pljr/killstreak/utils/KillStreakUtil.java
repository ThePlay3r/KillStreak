package me.pljr.killstreak.utils;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.config.CfgKillStreaks;
import me.pljr.killstreak.config.CfgOptions;
import me.pljr.killstreak.database.QueryManager;
import me.pljr.killstreak.managers.KillStreakManager;
import me.pljr.killstreak.managers.PlayerManager;
import me.pljr.killstreak.managers.SoundManager;
import me.pljr.killstreak.managers.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;

public class KillStreakUtil {
    private static final QueryManager query = KillStreak.getQueryManager();

    public static LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();

    public static void updateLeaderboard(){
        query.loadLeaderboard();
    }

    public static void autoUpdateLeaderboard(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(KillStreak.getInstance(), KillStreakUtil::updateLeaderboard, 0, CfgOptions.autoupdate * 20);
    }

    public static void kill(Player killer, Player victim){
        String killerName = killer.getName();
        Location killerLoc = killer.getLocation();
        PlayerManager killerManager = PlayerUtil.getPlayerManager(killerName);
        String victimName = victim.getName();
        PlayerManager victimManager = PlayerUtil.getPlayerManager(victimName);

        int killerKillstreak = killerManager.getKillstreak()+1;
        KillStreakManager killStreak = CfgKillStreaks.killstreaks.get(killerKillstreak);

        if (killStreak != null){
            TitleManager title = killStreak.getTitle();
            SoundManager sound = killStreak.getSound();
            boolean broadcast = killStreak.isBroadcast();
            List<String> broadcastMessage = killStreak.getBroadcastMessage();

            killer.sendTitle(title.getTitle()
                            .replace("%killer", killerName)
                            .replace("%victim", victimName)
                            .replace("%killstreak", killerKillstreak+"")
                    , title.getSubtitle()
                            .replace("%killer", killerName)
                            .replace("%victim", victimName)
                            .replace("%killstreak", killerKillstreak+"")
                    , title.getIn(), title.getStay(), title.getOut());
            killer.playSound(killerLoc, sound.getType(), sound.getVolume(), sound.getPitch());
            if (broadcast){
                for (String message :broadcastMessage){
                    final String replace = message
                            .replace("%killer", killerName)
                            .replace("%victim", victimName)
                            .replace("%killstreak", killerKillstreak + "");
                    if (CfgOptions.bungee){
                        BungeeUtil.broadcastMessage(replace);
                    }else{
                        Bukkit.broadcastMessage(replace);
                    }
                }
            }
        }

        killerManager.setKillstreak(killerKillstreak);
        killerManager.setLastKilled(victimName);

        victimManager.setKillstreak(0);

        PlayerUtil.setPlayerManager(killerName, killerManager);
        PlayerUtil.setPlayerManager(victimName, victimManager);
        query.savePlayer(killerName);
        query.savePlayer(victimName);
    }
}
