package service;

import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import service.exception.DuplicateEntryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormService {
    private static final File studentDB = new File("student-db.dep7");
    private static final File paymentDB = new File("payment-db.dep7");
    private static ArrayList<Student> STUDENT_LIST = new ArrayList<>();
    private static ArrayList<Payment> PAYMENTS = new ArrayList<>();

    static {
        readDataFromFile();
    }

    private static void readDataFromFile() {
        if (!(studentDB.exists() || paymentDB.exists())) return;

        try (FileInputStream fosStudent = new FileInputStream(studentDB);
             FileInputStream fosPayment = new FileInputStream(paymentDB);
             ObjectInputStream oosStudent = new ObjectInputStream(fosStudent);
             ObjectInputStream oosPayment = new ObjectInputStream(fosPayment)) {

            STUDENT_LIST = (ArrayList<Student>) oosStudent.readObject();
            PAYMENTS = (ArrayList<Payment>) oosPayment.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean savePayments(Student student, Payment payment) throws DuplicateEntryException {
        for (Student student1 : STUDENT_LIST) {
            for (Payment payment1 : PAYMENTS) {
                if (student.getNic().equals(student1.getNic()) && student.getCourseID().equals(student1.getCourseID())) {
                    if (payment1.getPaymentRadio().equals(payment.getPaymentRadio()) && !payment.getPaymentRadio().equals("Installment")) {
                        throw new DuplicateEntryException();
                    }
                }
            }
        }
        boolean addedStudent = STUDENT_LIST.add(student);
        boolean addedPayment = PAYMENTS.add(payment);
        boolean saved = writeDataFile();
        if (!saved) {
            STUDENT_LIST.remove(student);
            PAYMENTS.remove(payment);
        }

        return addedPayment && addedStudent && saved;
    }

    public List<PaymentFormTM> findAll() {
        List<PaymentFormTM> tm = new ArrayList<>();
        int count = 0;
        for (Student student : STUDENT_LIST) {

            tm.add(new PaymentFormTM(student.getCourseID(), student.getNic(), PAYMENTS.get(count).getAmount()));
            count++;
        }
        return tm;
    }

    private boolean writeDataFile() {
        try (FileOutputStream fosStudent = new FileOutputStream(studentDB);
             FileOutputStream fosPayment = new FileOutputStream(paymentDB);
             ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent);
             ObjectOutputStream oosPayment = new ObjectOutputStream(fosPayment)) {

            oosStudent.writeObject(STUDENT_LIST);
            oosPayment.writeObject(PAYMENTS);

        } catch (Throwable e) {
            return false;
        }
        return true;
    }

}
