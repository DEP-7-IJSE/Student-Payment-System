/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.menu;

import model.Payment;
import model.Student;
import model.tm.ManagePaymentTM;
import service.exception.DuplicateEntryException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ManagePaymentService {
    private static final List<Payment> PAYMENT= new ArrayList<>();
    private static final List<Student> STUDENT= new ArrayList<>();

    static {
        /*Payment p1 = new Payment("468464684v","Card",525,"Registration","05-10","Sehansa");
        Payment p2 = new Payment("468462584v","Card",45,"Registration","05-10","Pethum");
        Payment p3 = new Payment("468465884v","Cash",265,"Installment","05-10","Kavindu");
        PAYMENT.add(p1);
        PAYMENT.add(p2);
        PAYMENT.add(p3);*/

        Student s1 = new Student("468464684v","Niroth","Panadura","055-5644045","fhcueif@gmail.com","Nothing","DEP7");
        Student s2 = new Student("468465884v","Jeewantha","Galle","055-5625045","2522@gmail.com","Nothing","DEP8");
        Student s3 = new Student("468462584v","Aruni","Panadura","055-5644045","fh2857cueif@gmail.com","Nothing","DEP17");
        STUDENT.add(s1);
        STUDENT.add(s2);
        STUDENT.add(s3);
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
                break;
            }
        }
    }

    public void loadPayment(String tableNIC,String ... update) throws DuplicateEntryException {
        for (Payment payment : PAYMENT) {
            for (Student student : STUDENT) {
                if(tableNIC.equals(student.getNic()) && tableNIC.equals(payment.getNic())){
                    if(student.getCourseID().equals(update[0])){
                        throw new DuplicateEntryException();
                    }
                    student.setCourseID(update[0]);
                    payment.setAmount(new BigDecimal(update[1]));
                }
            }
        }
        loadAllPayments("");
    }
}
