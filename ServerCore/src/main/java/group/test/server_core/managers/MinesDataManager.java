package group.test.server_core.managers;

import group.test.server_core.Server_core;
import group.test.server_core.misc.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class MinesDataManager {
    Plugin plugin = Server_core.getPlugin(Server_core.class);

    public void fileCheck(){

        File f = new File("plugins/Mines", File.separator + "start2.yml");
        FileConfiguration mineData = YamlConfiguration.loadConfiguration(f);

        if (!f.exists()) {
            try {

                mineData.createSection("position");
                mineData.set("position.1.x", 1.0);
                mineData.set("position.1.y", 0.0);
                mineData.set("position.1.z", 0.0);
                mineData.set("position.1.world", "world");
                mineData.set("position.2.x", 100.0);
                mineData.set("position.2.y", 100.0);
                mineData.set("position.2.z", 100.0);
                mineData.set("position.2.world", "world");
                List<String> list = new ArrayList<String>();
                list.add("STONE-100");
                mineData.set("blocks", list);
                mineData.set("reset_time", "2 minute");
                mineData.save(f);

            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

    }

    public String GetMine(Location p){
        File dataFolder = new File("plugins/Mines/");
        assert p != null;
        assert dataFolder.listFiles() != null;
        for(Object st : dataFolder.listFiles()){
            String mine = st.toString();
            assert mine != null;
            mine = mine.replace("plugins\\Mines\\" , "");
            if(mine.endsWith(".yml")){
                mine = mine.replace(".yml" , "");
                double x1 = Double.parseDouble(GetData(mine, "position.1.x"));
                double y1 = Double.parseDouble(GetData(mine, "position.1.y"));
                double z1 = Double.parseDouble(GetData(mine, "position.1.z"));

                double x2 = Double.parseDouble(GetData(mine, "position.2.x"));
                double y2 = Double.parseDouble(GetData(mine, "position.2.y"));
                double z2 = Double.parseDouble(GetData(mine, "position.2.z"));
                double temp;
                if(x2 < x1){
                    temp = x2;
                    x2 = x1;
                    x1 = temp;
                }
                if(y2 < y1){
                    temp = y2;
                    y2 = y1;
                    y1 = temp;
                }
                if(z2 < z1){
                    temp = z2;
                    z2 = z1;
                    z1 = temp;
                }
                if(x1 <= p.getX() && p.getX() <= x2){
                    if(y1 <= p.getY() && p.getY() <= y2){
                        if(z1 <= p.getZ() && p.getZ() <= z2){
                            return mine;
                        }
                    }
                }
            }
        }
        return null;
    }

    public File[] GetMines(){
        File dataFolder = new File("plugins/Mines/");
        assert dataFolder.listFiles() != null;
        return dataFolder.listFiles();
    }

    public void ResetMine(String mine){
        assert mine != null;
        mine = mine.toLowerCase();
        File f = new File("plugins/Mines", File.separator + mine + ".yml");
        FileConfiguration mineData = YamlConfiguration.loadConfiguration(f);

        if (f.exists()) {
            double x1 = Double.parseDouble(GetData(mine, "position.1.x"));
            double y1 = Double.parseDouble(GetData(mine, "position.1.y"));
            double z1 = Double.parseDouble(GetData(mine, "position.1.z"));
            String world1 = GetData(mine,"position.1.world");
            if(world1.equalsIgnoreCase("NOT_SET")){
                world1 = "world";
            }
            Location loc1 = new Location(Bukkit.getWorld(world1), x1, y1, z1);

            double x2 = Double.parseDouble(GetData(mine, "position.2.x"));
            double y2 = Double.parseDouble(GetData(mine, "position.2.y"));
            double z2 = Double.parseDouble(GetData(mine, "position.2.z"));
            String world2 = GetData(mine,"position.1.world");
            if(world2.equalsIgnoreCase("NOT_SET")){
                world2 = "world";
            }
            Location loc2 = new Location(Bukkit.getWorld(world2), x2, y2, z2);
            Cuboid cuboid = new Cuboid(loc1, loc2);
            String s = "AIR";
            ArrayList<String> blocks = new ArrayList<>();
            for(Object st : mineData.getList("blocks")){
                String raw = (String) st;
                String[] arr =  raw.split("-");
                int chance = Integer.parseInt(arr[1]);
                for(int i = 0; i <= chance; i++){
                    blocks.add(arr[0]);
                }
            }
            for (Block block : cuboid) {
                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        int size = blocks.size();
                        int random = new Random().nextInt(size);
                        if (random == 1){
                            random -= 1;
                        }
                        Material blockss = Material.valueOf(blocks.get(random));
                        block.setType(blockss);
                    }
                }, 10);

            }
        }

    }
    public String CreateMine(String mine){
        mine = mine.toLowerCase();
        File f = new File("plugins/Mines", File.separator + mine + ".yml");
        FileConfiguration mineData = YamlConfiguration.loadConfiguration(f);

        if (!f.exists()) {
            try {

                mineData.createSection("position");
                mineData.set("position.1.x", 0.0);
                mineData.set("position.1.y", 1.0);
                mineData.set("position.1.z", 3.0);
                mineData.set("position.1.world", "world");
                mineData.set("position.2.x", 100.0);
                mineData.set("position.2.y", 100.0);
                mineData.set("position.2.z", 100.0);
                mineData.set("position.2.world", "world");
                mineData.set("blocks.STONE", "100.0");
                mineData.set("reset_time", "1 minute");
                mineData.save(f);
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }
        return "MINE_EXISTS";

    }

    public void SaveData(String mine, ArrayList<String> indices, ArrayList<String> values){
        mine = mine.toLowerCase();
        File f = new File("plugins/Mines", File.separator + mine + ".yml");
        FileConfiguration mineData = YamlConfiguration.loadConfiguration(f);

        if (f.exists()) {
            try {
                int iterator = 0;
                for (String t : indices){
                    mineData.set(t, values.get(iterator));
                    iterator += 1;
                }

                mineData.save(f);
            } catch (IOException exception) {

                exception.printStackTrace();
            }
        }

    }

    public String GetData(String mine, String value){
        mine = mine.toLowerCase();
        File f = new File("plugins/Mines/", mine + ".yml");
        FileConfiguration mineData = YamlConfiguration.loadConfiguration(f);

        if (f.exists()) {
            try {
                return Objects.requireNonNull(mineData.get(value)).toString();
            } catch (NullPointerException exception) {
                return "NOT_SET";
            }

        }
        return "!MINE_EXISTS";
    }
}
