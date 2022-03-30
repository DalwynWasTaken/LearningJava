package group.test.server_core.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import group.test.server_core.Server_core;
import group.test.server_core.database.MongoManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StatsManager {
    Plugin plugin = Server_core.getPlugin(Server_core.class);

    public String fileCheck(Player player) throws IOException {

        String playerName = player.getName();
        String playerUuid = player.getUniqueId().toString();
        File f = new File("plugins/Server_core/data", File.separator + playerUuid + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        if (!f.exists()) {
            new MongoManager().createDoc(player);
            System.out.println("New player joined!" + player.getDisplayName());
            File[] files = GetFiles();
            Integer i = 1;
            for(Object st : files) i++;
            playerData.set("Stats.Joined", i);
            playerData.save(f);
            return "NEW";
        }
        new MongoManager().saveData(player, "crystals", 100);
        return "JOINED";

    }

    public File[] GetFiles(){
        File dataFolder = new File("plugins/Server_core/data/");
        assert dataFolder.listFiles() != null;
        return dataFolder.listFiles();
    }

    public void SaveData(Player player, ArrayList<String> indices, ArrayList<String> values){

        String playerName = player.getName();
        String playerUuid = player.getUniqueId().toString();
        File f = new File("plugins/Server_core/data", File.separator + playerUuid + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        if (f.exists()) {
            try {
                int iterator = 0;
                for (String t : indices){
                    playerData.set("Stats." + t, values.get(iterator));
                    iterator += 1;
                }

                playerData.save(f);
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

    }

    public String GetData(Player player, String value){
        String playerName = player.getName();
        String playerUuid = player.getUniqueId().toString();
        File f = new File("plugins/Server_core/data", File.separator + playerUuid + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);

        if (f.exists()) {
            try{
                return Objects.requireNonNull(playerData.get(value)).toString();
            } catch (NullPointerException e){
                return "NOT_SET";
            }

        }
        return null;
    }
}
