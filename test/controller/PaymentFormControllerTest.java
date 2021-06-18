package controller;

import util.ValidationUtil;

public class PaymentFormControllerTest {

    public static void main(String[] args) {
        isValid("123456789v","pethum Jeewantha","Habarakada west, Tawalama,",
                "0767919355","500000");
    }

    public static void isValid(String nic,String name, String address, String contact, String amount){
        assert ValidationUtil.isValidNIC(nic):"Invalid nic";
        assert ValidationUtil.isValidName(name) : "Invalid name";
        assert ValidationUtil.isValidAddress(address) : "Invalid address";
        assert ValidationUtil.isValidContact(contact) : "Invalid contact";
        assert ValidationUtil.isValidAmount(amount) : "Invalid amount";
    }
}
