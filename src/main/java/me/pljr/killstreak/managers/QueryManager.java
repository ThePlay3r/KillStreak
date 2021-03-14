package me.pljr.killstreak.managers;

import lombok.AllArgsConstructor;
import me.pljr.killstreak.KillStreak;
import me.pljr.killstreak.config.Settings;
import me.pljr.killstreak.player.KStreakPlayer;
import me.pljr.killstreak.killstreak.KillStreakManager;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.K;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@AllArgsConstructor
public class QueryManager {
    private final DataSource dataSource;
    private final Settings settings;

    public KStreakPlayer loadPlayer(UUID uuid){
        KStreakPlayer player = new KStreakPlayer(uuid);
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM killstreak_players WHERE uuid=?"
            );
            preparedStatement.setString(1, uuid.toString());
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()){
                player = new KStreakPlayer(uuid, results.getInt("killstreak"), results.getString("lastkilled"));
            }
            dataSource.close(connection, preparedStatement, results);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return player;
    }

    public void savePlayer(KStreakPlayer kstreakPlayer){
        try {
            int killstreak = kstreakPlayer.getKillstreak();
            String lastkilled = kstreakPlayer.getLastKilled();

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "REPLACE INTO killstreak_players VALUES (?,?,?)"
            );
            preparedStatement.setString(1, kstreakPlayer.getUniqueId().toString());
            preparedStatement.setInt(2, killstreak);
            preparedStatement.setString(3, lastkilled);
            preparedStatement.executeUpdate();

            dataSource.close(connection, preparedStatement, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public LinkedHashMap<String, Integer> loadLeaderboard(){
        try {
            LinkedHashMap<String, Integer> sortedList;
            HashMap<String, Integer> everyone = new HashMap<>();

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM killstreak_players"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                everyone.put(PlayerUtil.getName(Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("uuid")))), resultSet.getInt("killstreak"));
            }

            sortedList = everyone.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            LinkedHashMap<String, Integer> leaderboard = new LinkedHashMap<>();
            int maxLoop = settings.getLeaderboard();
            int loop = 1;
            for (Map.Entry<String, Integer> entry : sortedList.entrySet()){
                if (loop == maxLoop) break;
                loop++;
                leaderboard.put(entry.getKey(), entry.getValue());
            }
            dataSource.close(connection, preparedStatement, resultSet);
            return leaderboard;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new LinkedHashMap<>();
    }

    public void setupTables() {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS killstreak_players (" +
                            "uuid char(36) NOT NULL PRIMARY KEY," +
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
