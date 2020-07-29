package me.pljr.killstreak.config;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.managers.ConfigManager;

public class CfgMysql {
    private final static ConfigManager mngr = KillStreak.getConfigManager();

    public static String host, database, username, password, port;

    public static void load(){
        CfgMysql.host = mngr.getString("mysql.host");
        CfgMysql.database = mngr.getString("mysql.database");
        CfgMysql.username = mngr.getString("mysql.username");
        CfgMysql.password = mngr.getString("mysql.password");
        CfgMysql.port = mngr.getString("mysql.port");
    }
}
