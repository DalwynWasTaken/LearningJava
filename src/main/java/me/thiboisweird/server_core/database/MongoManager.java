package me.thiboisweird.server_core.database;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import me.thiboisweird.server_core.Server_core;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MongoManager {
    Plugin plugin = Server_core.getPlugin(Server_core.class);
    public void saveData(Player player, String id, Object value){
        BasicDBObject query = new BasicDBObject();
        query.put("_uuid", player.getUniqueId().toString()); // (1)

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(id, value); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        new MongoDB().getCollection().updateOne(query, updateObject);
    }
    public void createDoc(Player player){
        Document person = new Document("_uuid", player.getUniqueId().toString())
                .append("name", player.getDisplayName())
                .append("money", 0)
                .append("crystals", 0)
                .append("prefix", "&7")
                .append("chatcolor", "&7")
                .append("belowname", "%player_ping%");
        new MongoDB().getCollection().insertOne(person);
    }

    public Object getData(Player player, String id){
        BasicDBObject query = new BasicDBObject();
        query.put("_uuid", player.getUniqueId().toString()); // (1)
        Document person = new MongoDB().getCollection().find(query).first();
        return person.get(id);
    }

    public void deleteDoc(Player player){
        BasicDBObject query = new BasicDBObject();
        query.put("_uuid", player.getUniqueId().toString()); // (1)
        new MongoDB().getCollection().deleteOne(query);
    }

    public void updateData(Player player, String id, Object value){
        BasicDBObject query = new BasicDBObject();
        query.put("_uuid", player.getUniqueId().toString()); // (1)
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(id, value); // (2)
        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)
        new MongoDB().getCollection().updateOne(query, updateObject);
    }


}