package com.example;

import redis.clients.jedis.Jedis;

public class RedisTest {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost", 6379);
        String response = jedis.ping();
        System.out.println(response); // PONG
    }

}
