package me.pljr.killstreak.objects;

import me.pljr.pljrapi.objects.PLJRSound;
import me.pljr.pljrapi.objects.PLJRTitle;

import java.util.List;

public class KillStreak {
    private final int start;
    private final int end;
    private final boolean broadcast;
    private final List<String> broadcastMessage;
    private final PLJRTitle title;
    private final PLJRSound sound;

    public KillStreak(int start, int end, boolean broadcast, List<String> broadcastMessage, PLJRTitle title, PLJRSound sound){
        this.start = start;
        this.end = end;
        this.broadcast = broadcast;
        this.broadcastMessage = broadcastMessage;
        this.title = title;
        this.sound = sound;
    }

    public int getStart() {
        return start;
    }

    public PLJRTitle getTitle() {
        return title;
    }

    public PLJRSound getSound() {
        return sound;
    }

    public int getEnd() {
        return end;
    }

    public List<String> getBroadcastMessage() {
        return broadcastMessage;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
