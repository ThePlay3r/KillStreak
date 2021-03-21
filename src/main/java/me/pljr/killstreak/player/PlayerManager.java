package me.pljr.killstreak.player;

import lombok.AllArgsConstructor;
import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.player.KStreakPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@AllArgsConstructor
public class PlayerManager {
    private final static int AUTOSAVE = 12000;

    private final HashMap<UUID, KStreakPlayer> players = new HashMap<>();
    private final JavaPlugin plugin;
    private final QueryManager queryManager;
    private final boolean cachePlayers;

    public void getPlayer(UUID uuid, Consumer<KStreakPlayer> consumer){
        if (!players.containsKey(uuid)){
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                KStreakPlayer scorePlayer = queryManager.loadPlayer(uuid);
                setPlayer(uuid, scorePlayer);
                consumer.accept(scorePlayer);
            });
        }else{
            consumer.accept(players.get(uuid));
        }
    }

    public void setPlayer(UUID uuid, KStreakPlayer scorePlayer){
        players.put(uuid, scorePlayer);
    }

    public void savePlayer(UUID uuid){
        queryManager.savePlayer(players.get(uuid));
        if (!cachePlayers) players.remove(uuid);
    }

    public void initAutoSave(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            KillStreak.log.info("Saving players..");
            for (Map.Entry<UUID, KStreakPlayer> entry : players.entrySet()){
                savePlayer(entry.getKey());
            }
            KillStreak.log.info("All players were saved.");
        }, AUTOSAVE, AUTOSAVE);
    }
}
