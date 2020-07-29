package me.pljr.killstreak.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.pljr.killstreak.KillStreak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BungeeUtil {
    private static final KillStreak instance = KillStreak.getInstance();

    public static void broadcastMessage(String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Message");
        out.writeUTF("ALL");
        out.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (player == null) return;
        player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
    }
}
