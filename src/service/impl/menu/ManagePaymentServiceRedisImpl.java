package service.impl.menu;

import map.Maps;
import model.tm.ManagePaymentTM;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManagePaymentServiceRedisImpl {

    private final Jedis client;

    public ManagePaymentServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public ArrayList<ManagePaymentTM> loadAllPayments(String query) {
        ArrayList<ManagePaymentTM> getPayments = new ArrayList<>();
        Set<String> data = client.keys("*");

        for (String nic : data) {
            if (!Character.isDigit(nic.charAt(0))) continue;
            if (nic.contains(query)) {
                getPayments.add(Maps.fromManagePaymentMap(nic, client.hgetAll(nic)));
            } else {
                List<String> hvals = client.hvals(nic);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        getPayments.add(Maps.fromManagePaymentMap(nic, client.hgetAll(nic)));
                        break;
                    }
                }
            }
        }
        return getPayments;
    }

    public void remove(ManagePaymentTM managePaymentTM) {
        client.del(managePaymentTM.getStudentNIC());
    }

    public void loadPayment(String tableNIC, String... update) throws DuplicateEntryException {
        if (client.exists(tableNIC) && client.exists(update[0])) throw new DuplicateEntryException();

        //client.hset(client.geta, )

        /*for (Payment payment : PAYMENT) {
            for (Student student : STUDENT) {
                if(tableNIC.equals(student.getNic()) && tableNIC.equals(payment.getNic())){
                    if(student.getCourseID().equals(update[0])){
                        throw new DuplicateEntryException();
                    }
                    student.setCourseID(update[0]);
                    payment.setAmount(Integer.parseInt(update[1])); //Todo: update
                }
            }
        }*/
        loadAllPayments("");
    }
}
