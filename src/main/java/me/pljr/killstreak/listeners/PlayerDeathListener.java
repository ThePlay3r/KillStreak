package me.pljr.killstreak.listeners;

import lombok.AllArgsConstructor;
import me.pljr.killstreak.killstreak.KillStreakManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {
    private final KillStreakManager streakManager;

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;
        streakManager.kill(killer, victim);
    }
}
