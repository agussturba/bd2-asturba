package org.example;

import redis.clients.jedis.Jedis;

public class RedisUtils {
    public static void createOrUpdateOneRedis(Jedis database, String key, String value){
        database.set(key, value);
    }

    public static String findOneRedis(Jedis database,String key) {
        return database.get(key);
    }
    public static void deleteOneRedis(Jedis database,String key){
        database.del(key);
    }
}
