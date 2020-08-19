package me.pljr.killstreak.listeners;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.QueryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        KillStreak.getPlayerManager().savePlayer(event.getPlayer().getUniqueId());
    }
}
