/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl2;

import model.tm.DashBoardTM;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashBoardServiceMYSQLImpl {

    private Connection connection;
    private Statement payments;
    private Statement cards;

    public DashBoardServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            payments = connection.createStatement();
            cards = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DashBoardTM> loadAll() throws SQLException {
        List<DashBoardTM> tm = new ArrayList<>();
        ResultSet rst = payments.executeQuery("SELECT course_id,`reg/full/inst`,amount,received_by FROM payment;");

        while (rst.next()) {
            tm.add(new DashBoardTM(
                    rst.getString("course_id"),
                    rst.getString("reg/full/inst"),
                    rst.getString("amount"),
                    rst.getString("received_by")
            ));
        }
        return tm;
    }

    public List<Integer> getCardDetails() throws SQLException {
        List<Integer> cardData = new ArrayList<>();
        ResultSet rst = cards.executeQuery("SELECT `reg/full/inst`,amount FROM payment;");
        int sum = 0;
        int registrations = 0;
        int payments = 0;

        while (rst.next()) {
            sum += rst.getInt("amount");
            if (rst.getString("reg/full/inst").equals("Registration")) {
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
