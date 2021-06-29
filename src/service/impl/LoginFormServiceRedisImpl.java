package service.impl;

import redis.clients.jedis.Jedis;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoginFormServiceRedisImpl {
    private static final String USER_PREFIX = "u#";
    private final Jedis client;

    public LoginFormServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        Set<String> mapUsers = client.keys(USER_PREFIX + "*");
        for (String mapUser : mapUsers) {
            users.add(mapUser.replace(USER_PREFIX, ""));
        }
        return users;
    }

    public String getCredentials(String user) {
        Set<String> mapUsers = client.keys(USER_PREFIX + user);
        return client.hget(USER_PREFIX + user, "password");
    }
}
