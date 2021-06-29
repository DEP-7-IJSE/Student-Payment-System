package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String userType;
    private String userName;
    private String password;

    public User(String userType, String userName, String password) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("type", userType);
        map.put("userName", userName);
        map.put("password", password);
        return map;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
