package service;

import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;

import java.util.ArrayList;
import java.util.List;

public class PaymentFormService {
    private static final ArrayList<Student> STUDENT_LIST= new ArrayList<>();
    private static final ArrayList<Payment> PAYMENTS = new ArrayList<>();

    public boolean savePayments(Student student, Payment payment){
        boolean addedStudent = STUDENT_LIST.add(student);
        boolean addedPayment = PAYMENTS.add(payment);
        return addedPayment && addedStudent;
    }

    public List<PaymentFormTM> findAll(){
        List<PaymentFormTM> tm = new ArrayList<>();
        int count=0;
        for (Student student : STUDENT_LIST) {
            tm.add(new PaymentFormTM(student.getCourseID(),student.getNic(),PAYMENTS.get(count).getAmount()));
            count++;
        }
        return tm;
    }

}