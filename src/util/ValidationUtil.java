/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package util;

public class ValidationUtil {

    public static boolean isValidNIC(String nic) {
        return nic.matches("(\\d{9}[vV])|(\\d{12})");
    }

    public static boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }

    public static boolean isValidUser(String user) {
        return user.matches("[A-Za-z]+-?[A-Za-z]+");
    }

    public static boolean isValidAddress(String address) {
        return address.matches("([A-Za-z\\d][ ,.:]*)+"); //Todo : Add - / marks
    }

    public static boolean isValidContact(String contact) {
        return contact.matches("(\\d{3})-?\\d{7}");
    }

    public static boolean isValidAmount(String amount) {
        return amount.matches("\\d{3,}(.(\\d{2}))?");
    }

    public static boolean isValidEmail(String email){
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|" +
                "\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
                "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|" +
                "\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static boolean isValidCourseID(String courseID){
        return courseID.matches("[A-Z]{3,4}\\d{1,3}");
    }

    public static boolean isValidBatchNb(String batch) {
        return batch.matches("\\d{1,3}");
    }

    public static boolean isValidStudentCount(String count) {
        return count.matches("\\d{1,3}");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("\\w{4,}");
    }

    /*public static boolean isInteger(String input) {
        if (input.startsWith("+") || input.startsWith("-")) {
            return false;
        }

        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValid(String input, boolean checkSpaces, boolean checkDigits, char... symbols) {
        char[] chars = input.toCharArray();

        loop1:
        for (char aChar : chars) {

            if ((checkDigits && Character.isDigit(aChar) || (checkSpaces && Character.isSpaceChar(aChar)))) {
                continue;
            }

            for (char symbol : symbols) {
                if (aChar == symbol) {
                    continue loop1;
                }
            }

            if (!Character.isAlphabetic(aChar)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmail(String input) {
        String[] split = input.split("@");

        if (split.length != 2) {
            return false;
        }

        String firstPart = split[0];
        int lastIndex = split[1].lastIndexOf('.');

        if (lastIndex == -1) {
            return false;
        }
        String secondPart = split[1].substring(0, lastIndex);
        String thirdPart = split[1].substring(lastIndex + 1);

        // @ijse.lk
        if (!(secondPart.length() >= 2 && thirdPart.length() >= 2)) {
            return false;
        }

        if (!isValid(firstPart, false, true, '.', '_') || (firstPart.startsWith(".") || firstPart.endsWith(".") || firstPart.startsWith("_") || firstPart.endsWith("_"))) {
            return false;
        }

        if (!(isValid(secondPart, false, true, '.') && !secondPart.startsWith(".") && !secondPart.endsWith("."))) {
            return false;
        }

        if (!isValid(thirdPart, false, true)) {
            return false;
        }

        return true;
    }*/
}
