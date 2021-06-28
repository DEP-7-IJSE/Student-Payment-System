package service.impl;

import map.Maps;
import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PaymentFormServiceRedisImpl {

    private final Jedis client;

    public PaymentFormServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public boolean savePayments(Student student, Payment payment) throws DuplicateEntryException {
        if (client.exists(student.getNic())) {
            throw new DuplicateEntryException();
        }
        /*if (student.getNic().equals(student1.getNic()) && student.getCourseID().equals(student1.getCourseID())) {
                    if(payment1.getPaymentRadio().equals(payment.getPaymentRadio()) && !payment.getPaymentRadio().equals("Installment")) {
                        throw new DuplicateEntryException();
                    }
                }*/ //Todo: validation
        client.hset(student.getNic(), Maps.toMap(student, payment));

        return true;
    }

    public List<PaymentFormTM> findAll() {
        List<PaymentFormTM> paymentFormTMList = new ArrayList<>();
        Set<String> nicList = client.keys("*");
        for (String nic : nicList) {
            paymentFormTMList.add(Maps.fromPaymentMap(nic, client.hgetAll(nic)));
        }
        return paymentFormTMList;
    }
}
