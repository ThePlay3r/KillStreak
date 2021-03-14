package me.pljr.killstreak.killstreak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.pljr.killstreak.config.KillStreaks;
import me.pljr.killstreak.config.Settings;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.player.KStreakPlayer;
import me.pljr.killstreak.player.PlayerManager;
import me.pljr.pljrapispigot.managers.TitleManager;
import me.pljr.pljrapispigot.objects.PLJRSound;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import me.pljr.pljrapispigot.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.List;
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
        Location killerLoc = killer.getLocation();
        playerManager.getPlayer(killerId, killerManager -> {
            UUID victimId = victim.getUniqueId();
            String victimName = victim.getName();
            playerManager.getPlayer(victimId, victimManager -> {

                int killerKillstreak = killerManager.getKillstreak()+1;
                KillStreak killStreak = killStreaks.getKillstreaks().get(killerKillstreak);

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
