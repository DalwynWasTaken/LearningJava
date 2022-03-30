package me.thiboisweird.server_core.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thiboisweird.server_core.Server_core;
import me.thiboisweird.server_core.managers.MinesDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mines implements CommandExecutor {
    Plugin plugin = Server_core.getPlugin(Server_core.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("Java.Mines")){
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You do not have the required permission &o(Java.Mines)&r&4!");
                p.sendMessage(message);
                return false;
            }
            if (args.length == 0){
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 /mines spawn\n&c[MINES]&4 /mines teleport\n&c[MINES]&4 /mines reset\n&c[MINES]&4 /mines create");
                p.sendMessage(message);
                return false;
            }
            MinesDataManager mdm = new MinesDataManager();
            if(args[0].equalsIgnoreCase("reload")){
                plugin.reloadConfig();
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a Reloaded the config!!");
                p.sendMessage(message);
                return false;
            }
            if(args[0].equalsIgnoreCase("papitest")){
                ArrayList<String> list = new ArrayList<>();
                list.add("ThiboIsWeird");
                list.add("N4hz");
                list.add("Cosmow0");
                list.add("Steve");
                for(String st : list){
                    String message = st + " is currently %ThiboHisCore_plr_online%";
                    p.sendMessage(PlaceholderAPI.setPlaceholders(Bukkit.getPlayerExact(st), message));
                }
            }
            if(args[0].equalsIgnoreCase("teleport")){
                if(args.length < 2){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You must specify a mine name! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                String exists = mdm.GetData(args[1], "position.1.x");
                if(exists.equalsIgnoreCase("!MINE_EXISTS")){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 This mine doesn't exist! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }

                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a You have been teleported! &o(" + args[1] + ")&r&a!");
                p.sendMessage(message);
                double x = Double.parseDouble(mdm.GetData(args[1], "position.1.x"));
                double y = Double.parseDouble(mdm.GetData(args[1], "position.1.y"));
                double z = Double.parseDouble(mdm.GetData(args[1], "position.1.z"));
                Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                p.teleport(loc);
            }
            if(args[0].equalsIgnoreCase("reset")){
                if(args.length < 2){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You must specify a mine name! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                String exists = mdm.GetData(args[1], "position.1.x");
                if(exists.equalsIgnoreCase("!MINE_EXISTS")){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 This mine doesn't exist! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                mdm.ResetMine(args[1]);
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a Resetting your mine! &o(" + args[1] + ")&r&a!");
                p.sendMessage(message);
                return false;

            }
            if(args[0].equalsIgnoreCase("create")){
                if(args.length < 2){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You must specify a mine name! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                String exists = mdm.CreateMine(args[1]);
                if(exists.equalsIgnoreCase("!MINE_EXISTS")){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 This mine doesn't exist! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a Created the mine! &o(" + args[1] + ")&r&4!");
                p.sendMessage(message);
                return false;
            }
            if(args[0].equalsIgnoreCase("mines")){
                File[] files = mdm.GetMines();
                if(files == null){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 There are no mines yet!");
                    p.sendMessage(message);
                    return false;
                }
                StringBuilder StringMines = new StringBuilder();
                StringMines.append(files[0].toString().replace("plugins\\Mines\\", "").replace(".yml", ""));
                String a = files[0].toString();
                for(Object st : files){
                    if(!st.toString().equalsIgnoreCase(a)){
                        StringMines.append(", ").append(st.toString().replace("plugins\\Mines\\", "").replace(".yml", ""));

                    }

                }
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a The mines are: &o" + StringMines);
                p.sendMessage(message);
                return false;
            }
            if(args[0].equalsIgnoreCase("spawn")){
                if(args.length < 2){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You must specify a mines name! &o(string)&r&4!");
                    p.sendMessage(message);

                    return false;

                }
                if(args.length < 3){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 You must specify 1 or 2! &o(int)&r&4!");
                    p.sendMessage(message);

                    return false;

                }
                String exists = mdm.GetData(args[1], "position." + args[2] + ".x");
                if(exists.equalsIgnoreCase("!MINE_EXISTS")){
                    String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&4 This mine doesn't exist! &o(string)&r&4!");
                    p.sendMessage(message);
                    return false;
                }
                String message = ChatColor.translateAlternateColorCodes('&', "&c[MINES]&a You have set the spawn! &o(" + args[1] + ")&r&a!");
                p.sendMessage(message);
                String x = Double.toString(p.getLocation().getX());
                String y = Double.toString(p.getLocation().getY());
                String z = Double.toString(p.getLocation().getZ());
                String world = p.getWorld().getName();
                ArrayList<String> indices = new ArrayList<>();
                indices.add("position." + args[2] + ".x");
                indices.add("position." + args[2] + ".y");
                indices.add("position." + args[2] + ".z");
                indices.add("position." + args[2] + ".world");
                ArrayList<String> values = new ArrayList<>();
                values.add(x);
                values.add(y);
                values.add(z);
                values.add(world);
                mdm.SaveData(args[1], indices, values);
            }
        }
        return false;
    }
}
