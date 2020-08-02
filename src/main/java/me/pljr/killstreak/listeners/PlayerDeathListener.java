package me.pljr.killstreak.listeners;

import me.pljr.killstreak.utils.KillStreakUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;
        KillStreakUtil.kill(killer, victim);
    }
}
