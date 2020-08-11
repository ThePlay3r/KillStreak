package me.pljr.killstreak.utils;

import me.pljr.killstreak.config.CfgKillStreaks;
import me.pljr.killstreak.config.CfgSettings;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.managers.PlayerManager;
import me.pljr.killstreak.objects.KillStreak;
import me.pljr.killstreak.objects.CorePlayer;
import me.pljr.pljrapi.managers.TitleManager;
import me.pljr.pljrapi.objects.PLJRSound;
import me.pljr.pljrapi.objects.PLJRTitle;
import me.pljr.pljrapi.utils.BungeeUtil;
import me.pljr.pljrapi.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class KillStreakUtil {
    private static final QueryManager query = me.pljr.killstreak.KillStreak.getQueryManager();

    public static LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();

    public static void updateLeaderboard(){
        query.loadLeaderboard();
    }

    public static void autoUpdateLeaderboard(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(me.pljr.killstreak.KillStreak.getInstance(), KillStreakUtil::updateLeaderboard, 0, CfgSettings.autoupdate * 20);
    }

    public static void kill(Player killer, Player victim){
        UUID killerId = killer.getUniqueId();
        String killerName = killer.getName();
        Location killerLoc = killer.getLocation();
        CorePlayer killerManager = PlayerManager.getCorePlayer(killerId);
        UUID victimId = victim.getUniqueId();
        String victimName = victim.getName();
        CorePlayer victimManager = PlayerManager.getCorePlayer(victimId);

        int killerKillstreak = killerManager.getKillstreak()+1;
        KillStreak killStreak = CfgKillStreaks.killstreaks.get(killerKillstreak);

        if (killStreak != null){
            PLJRTitle title = new PLJRTitle(
                    killStreak.getTitle().getTitle().replace("%Killstreak", killerKillstreak+""),
                    killStreak.getTitle().getSubtitle().replace("%killstreak", killerKillstreak+""),
                    killStreak.getTitle().getIn(), killStreak.getTitle().getStay(), killStreak.getTitle().getOut());
            PLJRSound sound = killStreak.getSound();
            boolean broadcast = killStreak.isBroadcast();
            List<String> broadcastMessage = killStreak.getBroadcastMessage();

            TitleManager.send(killer, title);
            killer.playSound(killerLoc, sound.getType(), sound.getVolume(), sound.getPitch());
            if (broadcast){
                for (String message : broadcastMessage){
                    final String replace = message
                            .replace("%killer", killerName)
                            .replace("%victim", victimName)
                            .replace("%killstreak", killerKillstreak + "");
                    ChatUtil.broadcast(replace);
                }
            }
        }

        killerManager.setKillstreak(killerKillstreak);
        killerManager.setLastKilled(victimName);

        victimManager.setKillstreak(0);

        PlayerManager.setCorePlayer(killerId, killerManager);
        PlayerManager.setCorePlayer(victimId, victimManager);
        query.savePlayer(killerId);
        query.savePlayer(victimId);
    }
}
