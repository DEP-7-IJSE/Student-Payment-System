package service.impl;

import maps.Maps;
import model.tm.DashBoardTM;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DashBoardServiceRedisImpl {

    private final Jedis client;

    public DashBoardServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public List<DashBoardTM> loadAll() {
        List<DashBoardTM> tm = new ArrayList<>();
        Set<String> nicList = client.keys("*");
        for (String nic : nicList) {
            tm.add(Maps.fromDashBoardMap(client.hgetAll(nic)));
        }
        return tm;
    }

    public List<Integer> getCardDetails() {
        List<Integer> cardData = new ArrayList<>();
        int sum = 0;
        int registrations = 0;
        int payments = 0;
        Set<String> keys = client.keys("*");
        for (String nic : keys) {
            Map<String, String> data = client.hgetAll(nic);
            sum += Integer.parseInt(data.get("amount"));
            if (data.get("what").equals("Registration")) {
                registrations++;
            }
            payments++;
        }
        cardData.add(registrations);
        cardData.add(payments);
        cardData.add(sum);
        return cardData;
    }
}
