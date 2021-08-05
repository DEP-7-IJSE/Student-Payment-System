/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl2;

import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import service.exception.DuplicateEntryException;
import util.DBConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormService {
    private static final ArrayList<Student> STUDENT_LIST = new ArrayList<>();
    private static final ArrayList<Payment> PAYMENTS = new ArrayList<>();

    private final Connection connection;

    public PaymentFormService() {
        connection = DBConnection.getInstance().getConnection();
    }

    public boolean savePayments(Student student, Payment payment) throws DuplicateEntryException {

        boolean addedStudent = STUDENT_LIST.add(student);
        boolean addedPayment = PAYMENTS.add(payment);
        return addedPayment && addedStudent;
    }

    public List<PaymentFormTM> findAll() {
        List<PaymentFormTM> tm = new ArrayList<>();
        int count = 0;
        for (Student student : STUDENT_LIST) {
            tm.add(new PaymentFormTM(student.getCourseID(), student.getNic(), PAYMENTS.get(count).getAmount().intValue()));
            count++;
        }
        return tm;
    }

}
