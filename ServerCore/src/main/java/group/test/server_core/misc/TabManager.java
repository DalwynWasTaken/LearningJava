package group.test.server_core.misc;

import group.test.server_core.Server_core;
import group.test.server_core.managers.StatsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import me.clip.placeholderapi.PlaceholderAPI;

import java.text.DecimalFormat;
import java.util.List;

public class TabManager {
    Plugin plugin = Server_core.getPlugin(Server_core.class);
    public void setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("ScoreBoard-1", "dummy", new Hex().translateHexCodes((plugin.getConfig().getString("server-prefix"))));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int lines_size = plugin.getConfig().getList("ScoreBoard").size();
        List<String> lines = plugin.getConfig().getStringList("ScoreBoard");
        int i = lines_size;
        Team a;
        new Lag();
        for(String s : lines){
            s = ChatColor.translateAlternateColorCodes('&', s);
            int ib = i-1;
            String st = "";
            while (ib != 0){
                st = st + " ";
                ib--;
            }

            a = board.registerNewTeam(st);
            a.addEntry(st);
            a.setPrefix(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', s.toString())));
            obj.getScore(st).setScore(i-1);
            i--;
        }
        player.setScoreboard(board);
    }

    public void updateScoreBoard(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(new Hex().translateHexCodes((plugin.getConfig().getString("server-prefix"))));

            int lines_size = plugin.getConfig().getList("ScoreBoard").size();
            List<String> lines = plugin.getConfig().getStringList("ScoreBoard");
            List<String> names = plugin.getConfig().getStringList("ScoreBoardNames");
            int i = lines_size;
            new Lag();
            DecimalFormat format = new DecimalFormat("0.0");
            for (String s : lines) {
                s = s.replace("{server-prefix}", new Hex().translateHexCodes((plugin.getConfig().getString("server-prefix"))));
                s = s.replace("{staff}", Integer.toString(i));
                s = s.replace("{tps}", format.format(Lag.getTPS()));
                s = ChatColor.translateAlternateColorCodes('&', s);

                int ib = i - 1;
                String st = "";
                while (ib != 0) {
                    st = st + " ";
                    ib--;
                }

                board.getTeam(st).setPrefix(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', s.toString())));
                i--;
            }
        }
    }

    public void setTablistHeader(){
        int i = 0;
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(pl.isOp()){
                i++;
            }
        }
        for(Player p : Bukkit.getOnlinePlayers()){
            Hex hxs = new Hex();
            Bukkit.getWorld("world").setTime(1000);
            Bukkit.getWorld("world").setClearWeatherDuration(10);
            List<String> raw2 = plugin.getConfig().getStringList("tablist-header");
            StringBuilder finals2 = new StringBuilder();
            for(String s : raw2){
                s = s.replace("{server-prefix}", hxs.translateHexCodes(plugin.getConfig().getString("server-prefix")));
                s = s.replace("{online}", Integer.toString(Bukkit.getOnlinePlayers().size()));
                s = s.replace("{max}", Integer.toString(Bukkit.getMaxPlayers()));
                s = s.replace("{player}", p.getDisplayName());
                s = s.replace("{staff}", Integer.toString(i));
                s = org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
                finals2.append(s.toString() + "\n");
            }
            p.setPlayerListHeader(finals2.toString());


        }
    }

    public void setTablistFooter(){
        int i = 0;
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(pl.isOp()){
                i++;
            }
        }
        Hex hxs = new Hex();
        List<String> raw = plugin.getConfig().getStringList("tablist-footer");
        StringBuilder finals = new StringBuilder();
        for(Player p : Bukkit.getOnlinePlayers()) {
            for (String s : raw) {
                s = s.replace("{server-prefix}", hxs.translateHexCodes(plugin.getConfig().getString("server-prefix")));
                s = s.replace("{online}", Integer.toString(Bukkit.getOnlinePlayers().size()));
                s = s.replace("{max}", Integer.toString(Bukkit.getMaxPlayers()));
                s = s.replace("{player}", p.getDisplayName());
                s = s.replace("{staff}", Integer.toString(i));
                s = org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
                finals.append(s.toString() + "\n");
            }
            p.setPlayerListFooter(finals.toString());
        }
    }

    public void setTablistName(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            StatsManager sm = new StatsManager();
            Hex hxs = new Hex();
            String prefix = org.bukkit.ChatColor.translateAlternateColorCodes('&', sm.GetData(p, "Stats.Prefix"));
            StringBuilder finale = new StringBuilder();
            if (prefix.equalsIgnoreCase("NOT_SET")) {
                prefix = "&7[DEFAULT]";
            }
            if (prefix.contains("#")) {
                prefix = hxs.translateHexCodes(prefix);

            }
            finale.append(prefix);
            finale.append(" ");
            String nick = org.bukkit.ChatColor.translateAlternateColorCodes('&', sm.GetData(p, "Stats.Nickname"));
            if (nick.equalsIgnoreCase("NOT_SET")) {
                nick = p.getDisplayName();
            }
            if (nick.contains("#")) {
                nick = hxs.translateHexCodes(nick);
            }
            finale.append(nick);
            p.setPlayerListName(finale.toString());
        }
    }



    public void createBelowName(Player player){

    }
}
