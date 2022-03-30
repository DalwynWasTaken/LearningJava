package group.test.server_core.managers;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class CurrencyManager {

    private ObjectId id;
    private Double money;
    @BsonProperty(value="player_uuid")
    private String playerUuid;
    private Double crystals;

    public CurrencyManager(String playerUuid, double money, double crystals) {
        this.playerUuid = playerUuid;
        this.money = money;
        this.crystals = crystals;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(String playerUuid) {
        this.playerUuid = playerUuid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getCrystals() {
        return crystals;
    }

    public void setCrystals(Double crystals) {
        this.crystals = crystals;
    }

}
