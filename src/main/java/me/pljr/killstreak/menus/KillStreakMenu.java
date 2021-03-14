package me.pljr.killstreak.menus;

import lombok.Getter;
import me.pljr.killstreak.config.MenuItem;
import me.pljr.killstreak.config.Lang;
import me.pljr.killstreak.killstreak.KillStreakManager;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class KillStreakMenu {

    private final GUI gui;

    public KillStreakMenu(Player player, KillStreakManager streakManager){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 3);

        ItemBuilder top1 = new ItemBuilder(MenuItem.MAIN_TOP1.get());
        ItemBuilder top2 = new ItemBuilder(MenuItem.MAIN_TOP2.get());
        ItemBuilder top3 = new ItemBuilder(MenuItem.MAIN_TOP3.get());

        List<String> othersList = new ArrayList<>();
        int loop = 0;
        for (Map.Entry<String, Integer> entry : streakManager.getLeaderboard().entrySet()){
            loop++;
            String name = entry.getKey();
            int kills = entry.getValue();
            switch (loop){
                case 1:
                    top1.withOwner(name);
                    top1.replaceName("{name}", name);
                    top1.replaceLore("{name}", name);
                    top1.replaceName("{kills}", kills+"");
                    top1.replaceLore("{kills}", kills+"");
                    continue;
                case 2:
                    top2.withOwner(name);
                    top2.replaceName("{name}", name);
                    top2.replaceLore("{name}", name);
                    top2.replaceName("{kills}", kills+"");
                    top2.replaceLore("{kills}", kills+"");
                    continue;
                case 3:
                    top3.withOwner(name);
                    top3.replaceName("{name}", name);
                    top3.replaceLore("{name}", name);
                    top3.replaceName("{kills}", kills+"");
                    top3.replaceLore("{kills}", kills+"");
                    continue;
            }
            othersList.add(Lang.LEADERBOARD_FORMAT.get().replace("{pos}", loop+"").replace("{name}", name).replace("{kills}", kills+""));
        }
        ItemStack leaderboardItem = new ItemBuilder(MenuItem.LEADERBOARD.get()).withLore(othersList).create();
        builder.setItem(12, top1.create());
        builder.setItem(13, top2.create());
        builder.setItem(14, top3.create());
        if (player.hasPermission("killstreak.update")){
            builder.setItem(16, new GUIItem(MenuItem.UPDATE.get(), click -> {
                streakManager.updateLeaderboard();
                player.closeInventory();
            }));
        }
        builder.setItem(22, leaderboardItem);

        this.gui = builder.create();
    }
}
