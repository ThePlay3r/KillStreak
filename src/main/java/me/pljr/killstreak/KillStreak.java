package me.pljr.killstreak;

import me.pljr.killstreak.commands.KillStreakCommand;
import me.pljr.killstreak.config.*;
import me.pljr.killstreak.player.PlayerManager;
import me.pljr.killstreak.managers.QueryManager;
import me.pljr.killstreak.listeners.AsyncPlayerPreLoginListener;
import me.pljr.killstreak.killstreak.KillStreakManager;
import me.pljr.killstreak.listeners.PlayerDeathListener;
import me.pljr.killstreak.listeners.PlayerQuitListener;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class KillStreak extends JavaPlugin {
    public static Logger log;

    private PLJRApiSpigot pljrApiSpigot;

    private PlayerManager playerManager;
    private KillStreakManager killStreakManager;
    private ConfigManager configManager;

    private QueryManager queryManager;

    private Settings settings;
    private KillStreaks killStreaks;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = this.getLogger();
        if (!setupPLJRApi()) return;
        setupConfig();
        setupDatabase();
        setupManagers();
        loadListeners();
        setupCommands();
    }

    public boolean setupPLJRApi(){
        if (PLJRApiSpigot.get() == null){
            getLogger().warning("PLJRApi-Spigot is not enabled!");
            return false;
        }
        pljrApiSpigot = PLJRApiSpigot.get();
        return true;
    }

    private void setupCommands(){
        getCommand("killstreak").setExecutor(new KillStreakCommand(killStreakManager));
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        killStreaks = new KillStreaks(configManager);
        settings = new Settings(configManager);
        MenuItem.load(new ConfigManager(this, "menus.yml"));
        Lang.load(new ConfigManager(this, "lang.yml"));
    }

    private void setupManagers(){
        playerManager = new PlayerManager(this, queryManager, settings.isCachePlayers());
        playerManager.initAutoSave();
        killStreakManager = new KillStreakManager(this, settings, killStreaks, queryManager, playerManager);
        killStreakManager.initUpdateLeaderboard();
    }

    private void setupDatabase(){
        DataSource dataSource = pljrApiSpigot.getDataSource(configManager);
        queryManager = new QueryManager(dataSource, settings);
        queryManager.setupTables();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(killStreakManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getOnlinePlayers().forEach(player -> playerManager.getPlayer(player.getUniqueId(), queryManager::savePlayer));
    }
}
