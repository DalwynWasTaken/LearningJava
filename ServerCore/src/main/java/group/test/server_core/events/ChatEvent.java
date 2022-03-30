package group.test.server_core.events;

import group.test.server_core.managers.StatsManager;
import group.test.server_core.misc.Hex;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatEvent implements Listener {

    @EventHandler
    public void ChatEvents(AsyncPlayerChatEvent e){
        StatsManager sm = new StatsManager();
        Hex hxs = new Hex();
        String prefix = ChatColor.translateAlternateColorCodes('&', sm.GetData(e.getPlayer(), "Stats.Prefix"));
        StringBuilder finale = new StringBuilder();
        if(prefix.equalsIgnoreCase("NOT_SET")){
            prefix = "&7[DEFAULT]";
        }
        if(prefix.contains("#")){
            prefix = hxs.translateHexCodes(prefix);

        }
        finale.append(prefix);
        finale.append(" ");
        String nick = ChatColor.translateAlternateColorCodes('&', sm.GetData(e.getPlayer(), "Stats.Nickname"));
        if(nick.equalsIgnoreCase("NOT_SET")){
            nick = e.getPlayer().getDisplayName();
        }
        if(nick.contains("#")){
            nick = hxs.translateHexCodes(nick);
        }
        finale.append(nick);
        finale.append(": ");
        String cc = ChatColor.translateAlternateColorCodes('&', sm.GetData(e.getPlayer(), "Stats.ChatColor"));
        if(cc.equalsIgnoreCase("NOT_SET")){
            cc = ChatColor.translateAlternateColorCodes('&', "&7");
        }
        if(cc.contains("#")){
            cc = hxs.translateHexCodes(cc);
        }
        finale.append(cc);



        e.setFormat(finale.toString() + e.getMessage());

    }

}