package me.pljr.killstreak.config;

import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.xseries.XMaterial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum MenuItem {
    MAIN_TOP1(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("&e1. &7{name}").withLore("&8► &f{kills}").withOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==").create()),
    MAIN_TOP2(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("&e2. &7{name}").withLore("&8► &f{kills}").withOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==").create()),
    MAIN_TOP3(new ItemBuilder(XMaterial.PLAYER_HEAD).withName("&e3. &7{name}").withLore("&8► &f{kills}").withOwner("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIzZWFlZmJkNTgxMTU5Mzg0Mjc0Y2RiYmQ1NzZjZWQ4MmViNzI0MjNmMmVhODg3MTI0ZjllZDMzYTY4NzJjIn19fQ==").create()),
    UPDATE(new ItemBuilder(XMaterial.REDSTONE).withName("&aUpdate").withLore("&8► &fClick for leaderboard update.").create()),
    LEADERBOARD(new ItemBuilder(XMaterial.OAK_SIGN).withName("&eOthers").create());

    private static HashMap<MenuItem, ItemStack> menuItem;
    private final ItemStack defaultValue;

    MenuItem(ItemStack defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        FileConfiguration fileConfig = config.getConfig();
        menuItem = new HashMap<>();
        for (MenuItem menuItem : values()){
            if (!fileConfig.isSet(menuItem.toString())){
                config.setSimpleItemStack(menuItem.toString(), menuItem.defaultValue);
            }else{
                MenuItem.menuItem.put(menuItem, config.getSimpleItemStack(menuItem.toString()));
            }
        }
        config.save();
    }

    public ItemStack get(){
        return menuItem.getOrDefault(this, defaultValue);
    }
}
