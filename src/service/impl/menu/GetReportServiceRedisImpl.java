package service.impl.menu;

import model.tm.GetReportTM;
import redis.clients.jedis.Jedis;
import util.JedisClient;

import java.util.ArrayList;
import java.util.Set;

public class GetReportServiceRedisImpl {

    private static final String PAYMENT_PREFIX = "p#";
    private final Jedis client;

    public GetReportServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public ArrayList<GetReportTM> loadAllData() {
        ArrayList<GetReportTM> getReport = new ArrayList<>();
        Set<String> dataList = client.keys(PAYMENT_PREFIX + "*");
        for (String nic : dataList) {
            getReport.add(GetReportTM.fromMap(nic, client.hgetAll(nic)));
        }
        return getReport;
    }
}
