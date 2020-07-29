package me.pljr.killstreak.listeners;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.database.QueryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final QueryManager query = KillStreak.getQueryManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        query.savePlayer(event.getPlayer().getName());
    }
}
