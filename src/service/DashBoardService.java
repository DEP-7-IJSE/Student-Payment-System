package service;

import model.Payment;
import model.Student;
import model.tm.DashBoardTM;

import java.util.ArrayList;
import java.util.List;

public class DashBoardService {
    private static final ArrayList<Student> STUDENT_LIST = new ArrayList<>();
    private static final ArrayList<Payment> PAYMENT_LIST = new ArrayList<>();

    static {
        Payment p1 = new Payment("468464684v","Card",525,"Registration","05-10","Sehansa");
        Payment p2 = new Payment("468462584v","Card",45,"Registration","05-10","Pethum");
        Payment p3 = new Payment("468465884v","Cash",265,"Installment","05-10","Kavindu");
        PAYMENT_LIST.add(p1);
        PAYMENT_LIST.add(p2);
        PAYMENT_LIST.add(p3);

        Student s1 = new Student("468464684v","Niroth","Panadura","055-5644045","fhcueif@gmail.com","Nothing","DEP7");
        Student s2 = new Student("468465884v","Jeewantha","Galle","055-5625045","2522@gmail.com","Nothing","DEP8");
        Student s3 = new Student("468462584v","Aruni","Panadura","055-5644045","fh2857cueif@gmail.com","Nothing","DEP17");
        STUDENT_LIST.add(s1);
        STUDENT_LIST.add(s2);
        STUDENT_LIST.add(s3);
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
