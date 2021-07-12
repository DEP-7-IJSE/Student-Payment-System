package controller;

import util.ValidationUtil;

public class ManageUserFormControllerTest {

    public static void main(String[] args) {
        isValid("pethum");
    }

    public static void isValid(String user) {
        assert ValidationUtil.isValidUser(user) : "Invalid user";
    }
}
