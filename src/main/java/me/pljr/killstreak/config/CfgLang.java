package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.enums.Lang;
import me.pljr.killstreak.managers.ConfigManager;

import java.util.HashMap;

public class CfgLang {
    private static final ConfigManager config = KillStreak.getConfigManager();

    public static HashMap<Lang, String> lang = new HashMap<>();

    public static void load(){
        for (Lang lang : Lang.values()){
            CfgLang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }
}
