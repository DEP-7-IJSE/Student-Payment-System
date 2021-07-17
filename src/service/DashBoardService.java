package service;

import model.Payment;
import model.Student;
import model.tm.DashBoardTM;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class DashBoardService {

    private static final File studentDB = new File("student-db.dep7");
    private static final File paymentDB = new File("payment-db.dep7");

    private static ArrayList<Student> STUDENT_LIST = new ArrayList<>();
    private static ArrayList<Payment> PAYMENT_LIST = new ArrayList<>();

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
            PAYMENT_LIST = (ArrayList<Payment>) oosPayment.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<DashBoardTM> loadAll() {
        List<DashBoardTM> tm = new ArrayList<>();
        for (Payment payment : PAYMENT_LIST) {
            for (Student student : STUDENT_LIST) {
                if(payment.getNic().equals(student.getNic())){
                    tm.add(new DashBoardTM(student.getCourseID(), payment.getPaymentRadio(), String.valueOf(payment.getAmount()), payment.getLogin()));
                }
            }
        }
        return tm;
    }

    public List<Integer> getCardDetails(){
        List<Integer> cardData = new ArrayList<>();
        int sum=0;
        int registrations=0;
        int payments=0;
        for (Payment payment : PAYMENT_LIST) {
            sum+=payment.getAmount();
            if(payment.getPaymentRadio().equals("Registration")){
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
