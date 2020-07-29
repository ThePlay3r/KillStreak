package me.pljr.killstreak.managers;

import org.bukkit.Sound;

public class SoundManager {
    private final Sound type;
    private final int volume;
    private final int pitch;

    public SoundManager(Sound type, int volume, int pitch){
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }
}
