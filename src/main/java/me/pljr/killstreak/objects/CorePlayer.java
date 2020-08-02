package me.pljr.killstreak.objects;

public class CorePlayer {
    private int killstreak;
    private String lastKilled;

    public CorePlayer(int killstreak, String lastKilled){
        this.killstreak = killstreak;
        this.lastKilled = lastKilled;
    }

    public int getKillstreak() {
        return killstreak;
    }

    public void setKillstreak(int killstreak) {
        this.killstreak = killstreak;
    }

    public String getLastKilled() {
        return lastKilled;
    }

    public void setLastKilled(String lastKilled) {
        this.lastKilled = lastKilled;
    }
}
