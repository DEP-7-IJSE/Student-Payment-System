package service.impl.menu;

import model.tm.ManagePaymentTM;
import redis.clients.jedis.Jedis;
import service.exception.NotFoundException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManagePaymentServiceRedisImpl {

    private static final String PAYMENT_PREFIX = "p#";
    private static final String STUDENT_PREFIX = "s#";
    private static final String COURSE_PREFIX = "c#";
    private final Jedis client;

    public ManagePaymentServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public ArrayList<ManagePaymentTM> loadAllPayments(String query) {
        ArrayList<ManagePaymentTM> getPayments = new ArrayList<>();
        Set<String> data = client.keys(PAYMENT_PREFIX + "*");

        for (String nic : data) {
            if (nic.contains(query)) {
                getPayments.add(ManagePaymentTM.fromMap(nic, client.hgetAll(nic)));
            } else {
                List<String> hvals = client.hvals(nic);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        getPayments.add(ManagePaymentTM.fromMap(nic, client.hgetAll(nic)));
                        break;
                    }
                }
            }
        }
        return getPayments;
    }

    public void remove(ManagePaymentTM managePaymentTM) {
        client.del(PAYMENT_PREFIX + managePaymentTM.getStudentNIC());
    }

    public boolean loadPayment(String tableNIC, String updateCourse, String updateAmount) throws NotFoundException {
        if (!client.exists(COURSE_PREFIX + updateCourse)) throw new NotFoundException();

        client.hset(PAYMENT_PREFIX + tableNIC, "courseID", updateCourse);
        client.hset(STUDENT_PREFIX + tableNIC, "courseID", updateCourse);
        client.hset(PAYMENT_PREFIX + tableNIC, "amount", updateAmount);

        loadAllPayments("");

        return true;
    }
}
