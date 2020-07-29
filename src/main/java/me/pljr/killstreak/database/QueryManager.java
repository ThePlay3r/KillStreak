package me.pljr.killstreak.database;

import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.config.CfgOptions;
import me.pljr.killstreak.managers.PlayerManager;
import me.pljr.killstreak.utils.KillStreakUtil;
import me.pljr.killstreak.utils.PlayerUtil;
import me.pljr.marriage.database.DataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryManager {
    private final KillStreak killStreak = KillStreak.getInstance();
    private final DataSource dataSource;

    public QueryManager(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void loadPlayer(String username){
        Bukkit.getScheduler().runTaskAsynchronously(killStreak, () ->{
            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM killstreak_players WHERE username=?"
                );
                preparedStatement.setString(1, username);
                ResultSet results = preparedStatement.executeQuery();
                int killstreak = 0;
                String lastkilled = "";
                if (results.next()){
                    killstreak = results.getInt("killstreak");
                    lastkilled = results.getString("lastkilled");
                }
                PlayerManager playerManager = new PlayerManager(killstreak, lastkilled);
                PlayerUtil.setPlayerManager(username, playerManager);
                dataSource.close(connection, preparedStatement, results);
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

    public void loadPlayerSync(String username){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM killstreak_players WHERE username=?"
            );
            preparedStatement.setString(1, username);
            ResultSet results = preparedStatement.executeQuery();
            int killstreak = 0;
            String lastkilled = "";
            if (results.next()){
                killstreak = results.getInt("killstreak");
                lastkilled = results.getString("lastkilled");
            }
            PlayerManager playerManager = new PlayerManager(killstreak, lastkilled);
            PlayerUtil.setPlayerManager(username, playerManager);
            dataSource.close(connection, preparedStatement, results);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void savePlayer(String username){
        Bukkit.getScheduler().runTaskAsynchronously(killStreak, () ->{
           try {
               PlayerManager playerManager = PlayerUtil.getPlayerManager(username);

               int killstreak = playerManager.getKillstreak();
               String lastkilled = playerManager.getLastKilled();

               Connection connection = dataSource.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(
                       "REPLACE INTO killstreak_players VALUES (?,?,?)"
               );
               preparedStatement.setString(1, username);
               preparedStatement.setInt(2, killstreak);
               preparedStatement.setString(3, lastkilled);
               preparedStatement.executeUpdate();

               dataSource.close(connection, preparedStatement, null);
           }catch (SQLException e){
               e.printStackTrace();
           }
        });
    }

    public void loadLeaderboard(){
        Bukkit.getScheduler().runTaskAsynchronously(killStreak, () ->{
           try {

               LinkedHashMap<String, Integer> sortedList;
               HashMap<String, Integer> everyone = new HashMap<>();

               Connection connection = dataSource.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(
                       "SELECT * FROM killstreak_players"
               );
               ResultSet resultSet = preparedStatement.executeQuery();
               while (resultSet.next()){
                   everyone.put(resultSet.getString("username"), resultSet.getInt("killstreak"));
               }

               sortedList = everyone.entrySet().stream()
                       .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

               LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();
               int maxLoop = CfgOptions.leaderboard;
               int loop = 1;
               for (Map.Entry<String, Integer> entry : sortedList.entrySet()){
                   if (loop == maxLoop) break;
                   loop++;
                   leaderboard.put(entry.getKey(), entry.getValue());
               }
               KillStreakUtil.leaderboard = leaderboard;

               dataSource.close(connection, preparedStatement, resultSet);
           }catch (SQLException e){
               e.printStackTrace();
           }
        });
    }

    public void setupTables() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS killstreak_players (" +
                            "username varchar(16) NOT NULL PRIMARY KEY," +
                            "killstreak int," +
                            "lastkilled varchar(16));"
            );
            preparedStatement.executeUpdate();
            dataSource.close(connection, preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
