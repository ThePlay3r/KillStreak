package me.pljr.killstreak.menus;

import me.pljr.killstreak.config.CfgLang;
import me.pljr.killstreak.config.CfgMenu;
import me.pljr.killstreak.enums.Lang;
import me.pljr.killstreak.utils.KillStreakUtil;
import me.pljr.pljrapi.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KillStreakMenu implements Listener {

    public static Inventory getMenu(Player player){
        Inventory inventory = Bukkit.createInventory(player, 3*9, CfgLang.lang.get(Lang.MENU_TITLE));

        ItemStack top1 = ItemStackUtil.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==", "", 1);
        ItemStack top2  = ItemStackUtil.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==", "", 1);
        ItemStack top3  = ItemStackUtil.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==", "", 1);
        List<String> othersList = new ArrayList<>();

        int loop = 0;
        for (Map.Entry<String, Integer> entry : KillStreakUtil.leaderboard.entrySet()){
            loop++;
            String name = entry.getKey();
            int kills = entry.getValue();
            switch (loop){
                case 1:
                    List<String> lore1 = new ArrayList<>();
                    for (String line : CfgMenu.top1Lore){
                        lore1.add(line.replace("%name", name).replace("%kills", kills+""));
                    }
                    top1 = ItemStackUtil.createHead(name,
                            CfgMenu.top1Name.replace("%name", name).replace("%kills", kills+""), 1, lore1);
                    continue;
                case 2:
                    List<String> lore2 = new ArrayList<>();
                    for (String line : CfgMenu.top2Lore){
                        lore2.add(line.replace("%name", name).replace("%kills", kills+""));
                    }
                    top2 = ItemStackUtil.createHead(name,
                            CfgMenu.top2Name.replace("%name", name).replace("%kills", kills+""), 1, lore2);
                    continue;
                case 3:
                    List<String> lore3 = new ArrayList<>();
                    for (String line : CfgMenu.top3Lore){
                        lore3.add(line.replace("%name", name).replace("%kills", kills+""));
                    }
                    top3 = ItemStackUtil.createHead(name,
                            CfgMenu.top3Name.replace("%name", name).replace("%kills", kills+""), 1, lore3);
                    continue;
            }
            othersList.add(CfgLang.lang.get(Lang.LEADERBOARD_FORMAT).replace("%pos", loop+"").replace("%name", name).replace("%kills", kills+""));
        }
        ItemStack others = CfgMenu.leaderboard;
        ItemMeta othersMeta = others.getItemMeta();
        othersMeta.setLore(othersList);
        others.setItemMeta(othersMeta);

        inventory.setItem(12, top1);
        inventory.setItem(13, top2);
        inventory.setItem(14, top3);
        if (player.hasPermission("killstreak.update")){
            inventory.setItem(16, CfgMenu.update);
        }
        inventory.setItem(22, others);

        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(CfgLang.lang.get(Lang.MENU_TITLE))){
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                if (slot == 16){
                    if (player.hasPermission("killstreak.update")){
                        KillStreakUtil.updateLeaderboard();
                        player.closeInventory();
                        player.openInventory(getMenu(player));
                    }
                }
            }
        }
    }
}
