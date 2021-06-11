package service;

import model.Payment;
import model.Student;

import java.util.ArrayList;

public class PaymentFormService {
    private static final ArrayList<Student> STUDENT_LIST= new ArrayList<>();
    private static final ArrayList<Payment> PAYMENTS = new ArrayList<>();

    public void savePayments(Student student, Payment payment){
        STUDENT_LIST.add(student);
        PAYMENTS.add(payment);
    }

}