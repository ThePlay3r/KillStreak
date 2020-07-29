package me.pljr.killstreak.listeners;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.database.QueryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {
    private final QueryManager query = KillStreak.getQueryManager();

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        query.loadPlayerSync(event.getName());
    }
}
