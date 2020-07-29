package me.pljr.killstreak;

import me.pljr.killstreak.commands.KillStreakCommand;
import me.pljr.killstreak.config.*;
import me.pljr.killstreak.database.QueryManager;
import me.pljr.killstreak.listeners.AsyncPlayerPreLoginListener;
import me.pljr.killstreak.menus.KillStreakMenu;
import me.pljr.killstreak.utils.KillStreakUtil;
import me.pljr.marriage.database.DataSource;
import me.pljr.killstreak.listeners.PlayerDeathListener;
import me.pljr.killstreak.listeners.PlayerQuitListener;
import me.pljr.killstreak.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillStreak extends JavaPlugin {
    private static KillStreak instance;
    private static ConfigManager configManager;
    private static DataSource dataSource;
    private static QueryManager queryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupConfig();
        setupDatabase();
        loadListeners();
        setupCommands();
        setupBungee();
    }

    private void setupBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    private void setupCommands(){
        getCommand("killstreak").setExecutor(new KillStreakCommand());
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(getConfig());
        CfgMysql.load();
        CfgKillStreaks.load();
        CfgMenu.load();
        CfgOptions.load();
        CfgLang.load();
    }

    private void setupDatabase(){
        dataSource = new DataSource();
        dataSource.load();
        dataSource.initPool();
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        KillStreakUtil.autoUpdateLeaderboard();
    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new KillStreakMenu(), this);
    }

    public static KillStreak getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static QueryManager getQueryManager() {
        return queryManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
