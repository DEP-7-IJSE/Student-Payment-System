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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormServiceMYSQLImpl {

    private Connection connection;
    private PreparedStatement saveStudent;
    private PreparedStatement savePayment;
    private PreparedStatement getPayment;
    private Statement getTM;
    private Statement getCourses;
    private Statement getStudents;

    public PaymentFormServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            saveStudent = connection.prepareStatement("INSERT INTO student " +
                    "VALUES (?,?,?,?,?,?,?);");
            savePayment = connection.prepareStatement("INSERT INTO payment " +
                    "(receipt_nb, nic, payment_method, amount, `reg/full/inst`, date, received_by, course_id) VALUES (?,?,?,?,?,?,?,?);");
            getPayment = connection.prepareStatement("SELECT `reg/full/inst` FROM payment WHERE course_id=? AND nic=?");
            getTM = connection.createStatement();
            getCourses = connection.createStatement();
            getStudents = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean savePayments(Student student, Payment payment) throws DuplicateEntryException {
        try {
            connection.setAutoCommit(false);

            saveStudent.setString(1, student.getNic());
            saveStudent.setString(2, student.getName());
            saveStudent.setString(3, student.getAddress());
            saveStudent.setString(4, student.getContact());
            saveStudent.setString(5, student.getEmail());
            saveStudent.setString(6, student.getDescription());
            saveStudent.setString(7, student.getCourseID());

            savePayment.setString(1, payment.getReceiptNb());
            savePayment.setString(2, payment.getNic());
            savePayment.setString(3, payment.getPaymentMethod());
            savePayment.setDouble(4, payment.getAmount().doubleValue());
            savePayment.setString(5, payment.getPaymentRadio());
            savePayment.setString(6, payment.getDate());
            savePayment.setString(7, payment.getLogin());
            savePayment.setString(8, payment.getCourseID());

            getPayment.setString(1, payment.getCourseID());
            getPayment.setString(2, payment.getNic());

            ResultSet rst = getPayment.executeQuery();
            while (rst.next()) {
                String exists = rst.getString("reg/full/inst");
                if (exists.equals("Registration") || exists.equals("Full Payment")) {
                    throw new DuplicateEntryException();
                }
            }

            if (savePayment.executeUpdate() != 1) {
                throw new RuntimeException("Payment Saving Failed");
            }

            ResultSet students = getStudents.executeQuery("SELECT nic FROM student WHERE nic='" + student.getNic() + "'");

            if (!students.next()) {
                if (saveStudent.executeUpdate() != 1) {
                    throw new RuntimeException("Student Saving Failed");
                }
            }

            return true;
        } catch (Throwable e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<PaymentFormTM> findAll() throws SQLException {
        List<PaymentFormTM> paymentFormTMList = new ArrayList<>();
        ResultSet rst = getTM.executeQuery("SELECT course_id,nic,amount FROM payment");
        while (rst.next()) {
            paymentFormTMList.add(new PaymentFormTM(rst.getString("course_id"), rst.getString("nic"), rst.getBigDecimal("amount")));
        }
        return paymentFormTMList;
    }

    public List<String> getAllCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        ResultSet rst = getCourses.executeQuery("SELECT course_id FROM course");

        while (rst.next()) {
            courses.add(rst.getString("course_id"));
        }

        return courses;
    }
}
