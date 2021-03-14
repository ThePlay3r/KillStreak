package me.pljr.killstreak.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class KStreakPlayer {
    private final UUID uniqueId;
    private int killstreak;
    private String lastKilled;

    public KStreakPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.killstreak = 0;
        this.lastKilled = "";
    }
}
