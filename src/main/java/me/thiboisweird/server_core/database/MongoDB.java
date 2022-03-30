package me.thiboisweird.server_core.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {
    ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:fbC3F0BtnQw7bihp@cluster0.z3uip.mongodb.net/test");
    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

    MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).codecRegistry(codecRegistry).build();

    MongoClient client = MongoClients.create(settings);
    MongoDatabase database = client.getDatabase("Server1");
    MongoCollection<Document> collection = database.getCollection("PlayerData");


    public MongoCollection<Document> getCollection(){
        return collection;
    }
}
