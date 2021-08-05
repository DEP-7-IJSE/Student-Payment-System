/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.tm.GetReportTM;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetReportServiceMYSQLImpl {

    private Connection connection;
    private Statement getReport;

    public GetReportServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            getReport = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GetReportTM> loadAllData() throws SQLException {
        ArrayList<GetReportTM> report = new ArrayList<>();
        ResultSet rst = getReport.executeQuery("SELECT * FROM payment");
        while (rst.next()) {
            report.add(new GetReportTM(
                    rst.getString("receipt_nb"),
                    rst.getString("date"),
                    rst.getString("nic"),
                    rst.getString("course_id"),
                    rst.getBigDecimal("amount"),
                    rst.getString("received_by")
            ));
        }
        return report;
    }

    public int getLastReceiptNb() throws SQLException {
        ResultSet rst = getReport.executeQuery("SELECT receipt_nb FROM payment");
        int max = 1;

        while (rst.next()) {
            String receiptNb = rst.getString("receipt_nb");
            int number = Integer.parseInt(receiptNb.split("R")[1]);
            if (max < number) {
                max = number;
            }
        }
        return max;
    }
}
