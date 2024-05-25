package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoUtils {

    public static void createOneMongo(MongoCollection<Document> mongoCollection, Document document){
        mongoCollection.insertOne(document);
    }
    public static void updateOneMongo(MongoCollection<Document> mongoCollection,Document filter, Document update){

        mongoCollection.updateOne(filter, update);
    }
    public static Document findOneMongo(MongoCollection<Document> mongoCollection,Document filter) {
        return mongoCollection.find(filter).first();
    }
    public static void deleteOneMongo(MongoCollection<Document> mongoCollection,Document filter){
        mongoCollection.deleteOne(filter);
    }

}
