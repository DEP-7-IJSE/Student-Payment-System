package model;

import java.util.HashMap;
import java.util.Map;

public class Payment {
    private String nic;
    private String paymentMethod;
    private int amount;
    private String paymentRadio;
    private String date;
    private String login;
    private String courseID;

    public Payment(String nic, String paymentMethod, int amount, String paymentRadio, String date, String login, String courseID) {
        this.nic = nic;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentRadio = paymentRadio;
        this.date = date;
        this.login = login;
        this.courseID = courseID;
    }

    public Payment() {
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentRadio() {
        return paymentRadio;
    }

    public void setPaymentRadio(String paymentRadio) {
        this.paymentRadio = paymentRadio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("paymentMethod", paymentMethod);
        map.put("amount", String.valueOf(amount));
        map.put("what", paymentRadio);
        map.put("date", date);
        map.put("login", login);
        map.put("courseID", courseID);
        return map;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "nic='" + nic + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", amount=" + amount +
                ", paymentRadio='" + paymentRadio + '\'' +
                ", date='" + date + '\'' +
                ", login='" + login + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }
}
