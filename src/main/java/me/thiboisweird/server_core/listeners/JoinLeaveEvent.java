package me.thiboisweird.server_core.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thiboisweird.server_core.Server_core;
import me.thiboisweird.server_core.database.MongoManager;
import me.thiboisweird.server_core.managers.CurrencyManager;
import me.thiboisweird.server_core.managers.StatsManager;
import me.thiboisweird.server_core.misc.Hex;
import me.thiboisweird.server_core.misc.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JoinLeaveEvent implements Listener {
    Plugin plugin = Server_core.getPlugin(Server_core.class);

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();

        e.setQuitMessage(ChatColor.GRAY+ "(" + ChatColor.RED + "-" + ChatColor.GRAY + ") " + ChatColor.GRAY + player.getDisplayName());

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();
        StatsManager sm = new StatsManager();
        String a = sm.fileCheck(player);
        File[] files = sm.GetFiles();
        int i = 0;
        for(Object st : files) i++;
        String message;
        Hex hxs = new Hex();
        if (a.equalsIgnoreCase("NEW")) {
            message = "&7(&2NEW&7) " + player.getDisplayName() + " &b#" + i;
            List<String> raw = plugin.getConfig().getStringList("welcome-new-message");
            for(String s : raw){
                s = s.replace("{server-prefix}", hxs.translateHexCodes(plugin.getConfig().getString("server-prefix")));
                s = s.replace("{online}", Integer.toString(Bukkit.getOnlinePlayers().size()));
                s = s.replace("{max}", Integer.toString(Bukkit.getMaxPlayers()));
                s = s.replace("{player}", e.getPlayer().getDisplayName());
                i = 0;
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.isOp()){
                        i++;
                    }
                }
                s = s.replace("{staff}", Integer.toString(i));
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
        } else {
            message = "&7(&a+&7) " + player.getDisplayName() + " &b#" + i;
            List<String> raw = plugin.getConfig().getStringList("welcome-back-message");
            for(String s : raw){
                s = s.replace("{server-prefix}", hxs.translateHexCodes(plugin.getConfig().getString("server-prefix")));
                s = s.replace("{online}", Integer.toString(Bukkit.getOnlinePlayers().size()));
                s = s.replace("{max}", Integer.toString(Bukkit.getMaxPlayers()));
                s = s.replace("{player}", e.getPlayer().getDisplayName());
                i = 0;
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.isOp()){
                        i++;
                    }
                }
                s = s.replace("{staff}", Integer.toString(i));
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
        }

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', message));
        new TabManager().setScoreBoard(e.getPlayer());
        new TabManager().createBelowName(e.getPlayer());
        e.getPlayer().sendMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), "You are %plr_online%"));
        e.getPlayer().sendMessage(new MongoManager().getData(e.getPlayer(), "money").toString());
        //new CurrencyManager(e.getPlayer().getUniqueId().toString(), (Double) new MongoManager().getData(e.getPlayer(), "money"), (Double) new MongoManager().getData(e.getPlayer(), "money"));
    }

}
