package service.impl;

import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PaymentFormServiceRedisImpl {

    private static final String STUDENT_PREFIX = "s#";
    private static final String PAYMENT_PREFIX = "p#";
    private final Jedis client;

    public PaymentFormServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public boolean savePayments(Student student, Payment payment) throws DuplicateEntryException {
        if (client.exists(STUDENT_PREFIX + student.getNic())) {
            throw new DuplicateEntryException();
        }
        /*if (student.getNic().equals(student1.getNic()) && student.getCourseID().equals(student1.getCourseID())) {
                    if(payment1.getPaymentRadio().equals(payment.getPaymentRadio()) && !payment.getPaymentRadio().equals("Installment")) {
                        throw new DuplicateEntryException();
                    }
                }*/ //Todo: validation
        client.hset(STUDENT_PREFIX + student.getNic(), student.toMap());
        client.hset(PAYMENT_PREFIX + payment.getNic(), payment.toMap());

        return true;
    }

    public List<PaymentFormTM> findAll() {
        List<PaymentFormTM> paymentFormTMList = new ArrayList<>();
        Set<String> nicList = client.keys(PAYMENT_PREFIX + "*");
        for (String nic : nicList) {
            paymentFormTMList.add(PaymentFormTM.fromMap(nic, client.hgetAll(nic)));
        }
        return paymentFormTMList;
    }
}
