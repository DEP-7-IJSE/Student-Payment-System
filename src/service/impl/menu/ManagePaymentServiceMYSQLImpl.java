/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.tm.ManagePaymentTM;
import service.exception.NotFoundException;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class ManagePaymentServiceMYSQLImpl {

    private Connection connection;
    private Statement deletePayment;
    private PreparedStatement updatePayment;

    public ManagePaymentServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            deletePayment = connection.createStatement();
            updatePayment = connection.prepareStatement("UPDATE payment SET course_id=?,amount=? WHERE nic=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ManagePaymentTM> loadAllPayments(String query) throws SQLException {
        ArrayList<ManagePaymentTM> getPayments = new ArrayList<>();
        String sql = "SELECT * FROM payment WHERE nic LIKE '%QUERY%' OR payment_method LIKE '%QUERY%' OR " +
                "`reg/full/inst` LIKE '%QUERY%' OR received_by LIKE '%QUERY%' OR course_id LIKE '%QUERY%'" +
                "OR date LIKE '%QUERY%' OR amount LIKE '%QUERY%';";
        sql = sql.replaceAll("QUERY", query);
        Statement getPayment = connection.createStatement();
        ResultSet rst = getPayment.executeQuery(sql);

        while (rst.next()) {
            getPayments.add(new ManagePaymentTM(
                    rst.getString("date"),
                    rst.getString("course_id"),
                    rst.getString("nic"),
                    rst.getBigDecimal("amount"),
                    rst.getString("received_by")
            ));
        }
        return getPayments;
    }

    public boolean remove(ManagePaymentTM managePaymentTM) throws SQLException {
        int affectedRows = deletePayment.executeUpdate("DELETE FROM payment WHERE nic='" + managePaymentTM.getStudentNIC() + "' AND " +
                "date='" + managePaymentTM.getDate() + "';");
        return affectedRows == 1;
    }

    public boolean updatePayment(String tableNIC, String updateCourse, String updateAmount) throws NotFoundException, SQLException {
        Statement getCourses = connection.createStatement();
        ResultSet course = getCourses.executeQuery("SELECT course_id FROM course WHERE course_id='" + updateCourse + "';");

        if (!course.next()) throw new NotFoundException();

        updatePayment.setString(1, updateCourse);
        updatePayment.setString(2, updateAmount);
        updatePayment.setString(3, tableNIC);

        return updatePayment.executeUpdate() == 1;
    }
}
