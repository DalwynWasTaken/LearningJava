package group.test.server_core.managers;

import org.bukkit.entity.Player;

import java.util.UUID;

public class RankManager {
    private String rank;
    private Player player;
    private String playerUuid;

    public void RankManager(Player player){
        this.player = player;
        try{
            this.rank = getRank(player);
        } catch (NullPointerException e){
            this.rank = "";
        }
    }

    public String getRank(Player player){
        return "a";
    }

    public void setPlayerUuid(String playerUuid) {
        this.playerUuid = playerUuid;
    }
}
