/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import service.exception.DuplicateEntryException;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageUserServiceMYSQLImpl {

    private Connection connection;
    private PreparedStatement saveUser;
    private PreparedStatement getUsers;
    private PreparedStatement deleteUser;

    public ManageUserServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            saveUser = connection.prepareStatement("INSERT INTO user " +
                    "(user_name, user_type, password) VALUES (?,?,?);");
            getUsers = connection.prepareStatement("SELECT user_name FROM user WHERE user_name=?;");
            deleteUser = connection.prepareStatement("DELETE FROM user WHERE user_name=?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveUser(User user) throws DuplicateEntryException, SQLException {
        getUsers.setString(1, user.getUserName());
        if (getUsers.executeQuery().next()) {
            throw new DuplicateEntryException();
        }
        saveUser.setString(1, user.getUserName());
        saveUser.setString(2, user.getUserType());
        saveUser.setString(3, DigestUtils.sha256Hex(user.getPassword()));

        return saveUser.executeUpdate() == 1;
    }

    public List<String> getUser(String query) throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_name LIKE '%QUERY%' OR user_type LIKE '%QUERY%';";
        sql = sql.replaceAll("QUERY", query);
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery(sql);

        while (rst.next()) {
            users.add(rst.getString("user_name"));
        }
        return users;
    }

    public boolean deleteUser(String user) throws SQLException {
        deleteUser.setString(1, user);
        return deleteUser.executeUpdate() == 1;
    }
}
