/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.tm.ManageStudentTM;
import util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManageStudentServiceMYSQLImpl {

    private final Connection connection;

    public ManageStudentServiceMYSQLImpl() {

        connection = DBConnection.getInstance().getConnection();
    }

    public List<ManageStudentTM> getAllStudent(String query) throws SQLException {

        String sql = "SELECT * FROM student WHERE nic LIKE '%QUERY%' OR name LIKE '%QUERY%' OR " +
                "course_id LIKE '%QUERY%' OR contact LIKE '%QUERY%' OR address LIKE '%QUERY%' OR " +
                "email LIKE '%QUERY%';";
        sql = sql.replaceAll("QUERY", query);

        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(sql);
        List<ManageStudentTM> tm = new ArrayList<>();


        while (rst.next()) {
            tm.add(new ManageStudentTM(
                    rst.getString("course_id"),
                    rst.getString("nic"),
                    rst.getString("name"),
                    rst.getString("contact"),
                    rst.getString("address"),
                    rst.getString("email")
            ));
        }
        return tm;
    }

    public void removeStudent(ManageStudentTM tm) {
        //client.del(STUDENT_PREFIX+tm.getNIC());
    }
}
