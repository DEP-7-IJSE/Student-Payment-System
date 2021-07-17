package service.menu;

import model.Payment;
import model.Student;
import model.tm.ManagePaymentTM;
import service.exception.DuplicateEntryException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManagePaymentService {
    private static final File studentDB = new File("student-db.dep7");
    private static final File paymentDB = new File("payment-db.dep7");
    private static List<Payment> PAYMENT = new ArrayList<>();
    private static List<Student> STUDENT = new ArrayList<>();

    static {
        readDataFromFile();
    }

    private static void readDataFromFile() {
        if (!(studentDB.exists() || paymentDB.exists())) return;

        try (FileInputStream fosStudent = new FileInputStream(studentDB);
             FileInputStream fosPayment = new FileInputStream(paymentDB);
             ObjectInputStream oosStudent = new ObjectInputStream(fosStudent);
             ObjectInputStream oosPayment = new ObjectInputStream(fosPayment)) {

            STUDENT = (ArrayList<Student>) oosStudent.readObject();
            PAYMENT = (ArrayList<Payment>) oosPayment.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ManagePaymentTM> loadAllPayments(String query){
        ArrayList<ManagePaymentTM> getPayments= new ArrayList<>();
        for (Payment payment : PAYMENT) {
            for (Student student : STUDENT) {
                if (payment.getNic().contains(query) || String.valueOf(payment.getAmount()).contains(query) ||
                        payment.getDate().contains(query) || payment.getLogin().contains(query) ||
                student.getCourseID().contains(query)) {
                    if (payment.getNic().equals(student.getNic())) {
                        getPayments.add(new ManagePaymentTM(payment.getDate(), student.getCourseID(), student.getNic(), payment.getAmount(), payment.getLogin()));
                        break;
                    }
                }
            }
        }
        return getPayments;
    }

    public void remove(ManagePaymentTM managePaymentTM){
        for (Payment payment : PAYMENT) {
            if (managePaymentTM.getCourseID().equals(payment.getNic())){
                PAYMENT.remove(payment);
                if (!writeDataFile()) PAYMENT.add(payment);
                break;
            }
        }
    }

    public void loadPayment(String tableNIC,String ... update) throws DuplicateEntryException {
        for (Payment payment : PAYMENT) {
            for (Student student : STUDENT) {
                if(tableNIC.equals(student.getNic()) && tableNIC.equals(payment.getNic())){
                    if (student.getCourseID().equals(update[0])) {
                        throw new DuplicateEntryException();
                    }
                    student.setCourseID(update[0]);
                    payment.setAmount(Integer.parseInt(update[1]));
                }
            }
        }
        loadAllPayments("");
    }

    private boolean writeDataFile() {
        try (FileOutputStream fosStudent = new FileOutputStream(studentDB);
             FileOutputStream fosPayment = new FileOutputStream(paymentDB);
             ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent);
             ObjectOutputStream oosPayment = new ObjectOutputStream(fosPayment)) {

            oosStudent.writeObject(STUDENT);
            oosPayment.writeObject(PAYMENT);

        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
