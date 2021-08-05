/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginFormServiceMYSQLImpl {

    private Connection connection;
    private Statement getUsers;
    private PreparedStatement authentication;
    private PreparedStatement getUserType;

    public LoginFormServiceMYSQLImpl() {
        try {
            connection = DBConnection.getInstance().getConnection();
            getUsers = connection.createStatement();
            authentication = connection.prepareStatement("SELECT password FROM user WHERE user_name=?");
            getUserType = connection.prepareStatement("SELECT user_type FROM user WHERE user_name=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        ResultSet user = getUsers.executeQuery("SELECT user_name FROM user");
        while (user.next()) {
            users.add(user.getString("user_name"));
        }
        return users;
    }

    public boolean authentication(String user, String password) throws SQLException {
        authentication.setString(1, user);
        ResultSet rst = authentication.executeQuery();
        rst.next();
        String savedPassword = rst.getString("password");
        String addedPassword = DigestUtils.sha256Hex(password);
        return addedPassword.equals(savedPassword);
    }

    public String getUserType(String user) throws SQLException {
        getUserType.setString(1, user);
        ResultSet rst = getUserType.executeQuery();
        rst.next();
        return rst.getString("user_type");
    }
}
