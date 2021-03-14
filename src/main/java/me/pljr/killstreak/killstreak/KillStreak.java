package me.pljr.killstreak.killstreak;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.pljr.pljrapispigot.objects.PLJRSound;
import me.pljr.pljrapispigot.objects.PLJRTitle;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class KillStreak {
    private final int start;
    private final int end;
    private final boolean broadcast;
    private final List<String> broadcastMessage;
    private final PLJRTitle title;
    private final PLJRSound sound;
}
