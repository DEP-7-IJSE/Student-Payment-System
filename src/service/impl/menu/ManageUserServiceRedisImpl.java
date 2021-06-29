package service.impl.menu;

import model.User;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import util.JedisClient;

public class ManageUserServiceRedisImpl {
    private static final String USER_PREFIX = "u#";
    private final Jedis client;

    public ManageUserServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void saveUser(User user) throws DuplicateEntryException {
        if (client.exists(user.getUserName())) {
            throw new DuplicateEntryException();
        }
        client.hset(USER_PREFIX + user.getUserName(), user.toMap());
    }
}
