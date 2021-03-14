package me.pljr.killstreak.listeners;

import lombok.AllArgsConstructor;
import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@AllArgsConstructor
public class AsyncPlayerPreLoginListener implements Listener {
    private final PlayerManager playerManager;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        playerManager.getPlayer(event.getUniqueId(), ignored -> KillStreak.log.info("Loaded " + event.getName()));
    }
}
