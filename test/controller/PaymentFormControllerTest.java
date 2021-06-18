package controller;

import util.ValidationUtil;

public class PaymentFormControllerTest {

    public static void main(String[] args) {
        isValid("123456789v","pethum Jeewantha","Habarakada west, Tawalama,");
    }

    public static void isValid(String nic,String name, String address){
        assert ValidationUtil.isValidNIC(nic):"Invalid nic";
        assert ValidationUtil.isValidName(name) : "Invalid name";
        assert ValidationUtil.isValidAddress(address) : "Invalid address";
    }
}
