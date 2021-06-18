package util;

public class ValidationUtil {

    public static boolean isValidNIC(String nic){
        return nic.matches("(\\d{9}[vV])|(\\d{12})");
    }

    public static boolean isValidName(String name){
        return name.matches("[A-Za-z ]+");
    }

    public static boolean isValidAddress(String address){
        return address.matches("([A-Za-z\\d][ ,.:]*)+");
    }


    public static boolean isInteger(String input) {
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
    }

}