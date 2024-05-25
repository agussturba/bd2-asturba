package org.example;
import com.datastax.oss.driver.api.core.CqlSession;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import redis.clients.jedis.Jedis;

import static org.example.MongoUtils.*;

public class Main {
    private static MongoClient mongoClient;
    private static Jedis redisClient;
    private static CqlSession cassandra;

    public static void main(String[] args) {
        MongoDatabase mongodb = connectToMongo();
        System.out.println(findOneMongo(mongodb.getCollection("coleccion"), new Document("name", "John")));
        connectToRedis();
        connectToCassandra();
        RedisUtils.createOrUpdateOneRedis(redisClient, "key", "value");
        System.out.println(RedisUtils.findOneRedis(redisClient, "key"));

    }
    //Dejo ejemplos de Document en la clase para que la vean
    private static MongoDatabase connectToMongo() {
        try  {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("Clase_04");
            database.createCollection("coleccion");
            /*Document document = new Document("name", "John")
                    .append("age", 30)
                    .append("city", "New York");
            Document filter = new Document("name", "John");
            Document update = new Document("$set", new Document("age", 35));*/
            return database;
        } catch (Exception e) {
            System.err.println("Error connecting to the MongoDB database: " + e.getMessage());
            return null;
        }
    }

    private static void connectToRedis() {
        try  {
            redisClient = new Jedis("localhost");
        } catch (Exception e) {
            System.err.println("Error connecting to Redis: " + e.getMessage());
        }
    }

    private static void connectToCassandra(){
        try {
            cassandra = CqlSession.builder().build();
        }catch (Exception e){
            System.err.println("Error connecting to Cassandra: " + e.getMessage());
        }
    }


}
