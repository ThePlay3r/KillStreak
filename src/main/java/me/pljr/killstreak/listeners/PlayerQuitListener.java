package me.pljr.killstreak.listeners;

import lombok.AllArgsConstructor;
import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {
    private final PlayerManager playerManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        playerManager.savePlayer(event.getPlayer().getUniqueId());
    }
}
