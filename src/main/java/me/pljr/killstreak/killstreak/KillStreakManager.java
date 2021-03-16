package me.pljr.killstreak.killstreak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.pljr.killstreak.config.KillStreaks;
import me.pljr.killstreak.config.Settings;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.player.PlayerManager;
import me.pljr.pljrapispigot.builders.TitleBuilder;
import me.pljr.pljrapispigot.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.UUID;

@RequiredArgsConstructor
public class KillStreakManager {
    private final JavaPlugin plugin;
    private final Settings settings;
    private final KillStreaks killStreaks;

    private final QueryManager query;
    private final PlayerManager playerManager;

    @Getter private LinkedHashMap<String, Integer> leaderboard;

    public void updateLeaderboard(){
        this.leaderboard = query.loadLeaderboard();
    }

    public void initUpdateLeaderboard(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::updateLeaderboard, 0, settings.getAutoUpdate() * 20L);
    }

    public void kill(Player killer, Player victim){
        UUID killerId = killer.getUniqueId();
        String killerName = killer.getName();
        playerManager.getPlayer(killerId, killerManager -> {
            UUID victimId = victim.getUniqueId();
            String victimName = victim.getName();
            playerManager.getPlayer(victimId, victimManager -> {

                int killerKillstreak = killerManager.getKillstreak()+1;
                KillStreak killStreak = killStreaks.getKillstreaks().get(killerKillstreak);

                if (killStreak != null){
                    new TitleBuilder(killStreak.getTitle())
                            .replaceTitle("{killstreak}", killerKillstreak+"")
                            .replaceSubtitle("{killstreak}", killerKillstreak+"")
                            .create().send(killer);
                    killStreak.getSound().play(killer);
                    boolean broadcast = killStreak.isBroadcast();
                    if (broadcast){
                        for (String message : killStreak.getBroadcastMessage()){
                            final String replace = message
                                    .replace("%killer", killerName)
                                    .replace("%victim", victimName)
                                    .replace("%killstreak", killerKillstreak + "");
                            ChatUtil.broadcast(replace, "", settings.isBungee());
                        }
                    }
                }

                killerManager.setKillstreak(killerKillstreak);
                killerManager.setLastKilled(victimName);

                victimManager.setKillstreak(0);

                playerManager.setPlayer(killerId, killerManager);
                playerManager.setPlayer(victimId, victimManager);
            });
        });
    }
}
