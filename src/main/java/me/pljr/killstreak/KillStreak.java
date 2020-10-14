package me.pljr.killstreak;

import me.pljr.killstreak.commands.KillStreakCommand;
import me.pljr.killstreak.config.*;
import me.pljr.killstreak.managers.PlayerManager;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.listeners.AsyncPlayerPreLoginListener;
import me.pljr.killstreak.menus.KillStreakMenu;
import me.pljr.killstreak.utils.KillStreakUtil;
import me.pljr.killstreak.listeners.PlayerDeathListener;
import me.pljr.killstreak.listeners.PlayerQuitListener;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.database.DataSource;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillStreak extends JavaPlugin {
    private static KillStreak instance;
    private static PlayerManager playerManager;
    private static ConfigManager configManager;
    private static DataSource dataSource;
    private static QueryManager queryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if (!setupPLJRApi()) return;
        setupConfig();
        setupManagers();
        setupDatabase();
        loadListeners();
        setupCommands();
    }

    private boolean setupPLJRApi(){
        PLJRApi api = (PLJRApi) Bukkit.getServer().getPluginManager().getPlugin("PLJRApi");
        if (api == null){
            Bukkit.getConsoleSender().sendMessage("§cKillStreak: PLJRApi not found, disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }else{
            Bukkit.getConsoleSender().sendMessage("§aKillStreak: Hooked into PLJRApi!");
            return true;
        }
    }

    private void setupCommands(){
        getCommand("killstreak").setExecutor(new KillStreakCommand());
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(getConfig(), "§cKillStreak:", "config.yml");
        dataSource = DataSource.getFromConfig(configManager);
        CfgKillStreaks.load();
        CfgMenu.load();
        CfgSettings.load();
        CfgLang.load();
    }

    private void setupManagers(){
        playerManager = new PlayerManager();
    }

    private void setupDatabase(){
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        KillStreakUtil.autoUpdateLeaderboard();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
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
    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.savePlayerSync(player.getUniqueId());
        }
    }
}
