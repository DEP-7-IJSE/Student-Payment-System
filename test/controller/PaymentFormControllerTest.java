package controller;

import util.ValidationUtil;

public class PaymentFormControllerTest {

    public static void main(String[] args) {
        isValid("123456789v","pethum Jeewantha","Habarakada west, Tawalama,",
                "0767919355","500000", "pethumjeewantha1@gmail.com","GDSE700");
    }

    public static void isValid(String nic,String name, String address, String contact, String amount, String email, String courseID){
        assert ValidationUtil.isValidNIC(nic):"Invalid nic";
        assert ValidationUtil.isValidName(name) : "Invalid name";
        assert ValidationUtil.isValidAddress(address) : "Invalid address";
        assert ValidationUtil.isValidContact(contact) : "Invalid contact";
        assert ValidationUtil.isValidAmount(amount) : "Invalid amount";
        assert ValidationUtil.isValidEmail(email) : "Invalid Email";
        assert ValidationUtil.isValidCourseID(courseID) : "Invalid CourseID";
    }
}
