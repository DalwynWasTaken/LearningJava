package group.test.server_core.events;

import group.test.server_core.managers.MinesDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineBlockBreak implements Listener {

    @EventHandler
    public void BlockBreak(BlockBreakEvent e){
        MinesDataManager mdm = new MinesDataManager();
        e.getPlayer().sendMessage("You broke a block in the " +mdm.GetMine(e.getBlock().getLocation()) + " mine!");
    }

}
