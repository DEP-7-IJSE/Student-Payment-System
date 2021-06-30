package service.impl.menu;

import model.tm.ManageStudentTM;
import redis.clients.jedis.Jedis;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageStudentServiceRedisImpl {

    private final Jedis client;
    private static final String STUDENT_PREFIX = "s#";

    public ManageStudentServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public List<ManageStudentTM> getAllStudent(String query) {
        List<ManageStudentTM> tm = new ArrayList<>();
        Set<String> data = client.keys(STUDENT_PREFIX + "*");
        for (String nic : data) {
            if (nic.contains(query)) {
                tm.add(ManageStudentTM.fromMap(nic, client.hgetAll(nic)));
            } else {
                List<String> hvals = client.hvals(nic);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        tm.add(ManageStudentTM.fromMap(nic, client.hgetAll(nic)));
                        break;
                    }
                }
            }
        }
        return tm;
    }

    public void removeStudent(ManageStudentTM tm) {
        //client.del(STUDENT_PREFIX+tm.getNIC());
    }
}
