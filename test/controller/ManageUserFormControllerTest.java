/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

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
