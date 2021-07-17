/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.User;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<String> getUser(String query) {
        List<String> users = new ArrayList<>();
        Set<String> data = client.keys(USER_PREFIX + "*");
        for (String user : data) {
            if (user.contains(query)) {
                users.add(User.fromMap(user));
            } else {
                List<String> hvals = client.hvals(user);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        users.add(User.fromMap(user));
                        break;
                    }
                }
            }
        }
        return users;
    }

    public void deleteUser(String user) {
        client.del(USER_PREFIX + user);
    }
}
