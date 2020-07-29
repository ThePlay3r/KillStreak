package me.pljr.killstreak.managers;

import java.util.List;

public class KillStreakManager {
    private final int start;
    private final int end;
    private final boolean broadcast;
    private final List<String> broadcastMessage;
    private final TitleManager title;
    private final SoundManager sound;

    public KillStreakManager(int start, int end, boolean broadcast, List<String> broadcastMessage, TitleManager title, SoundManager sound){
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

    public TitleManager getTitle() {
        return title;
    }

    public SoundManager getSound() {
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
