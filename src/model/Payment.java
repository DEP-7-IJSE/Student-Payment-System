package model;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class Payment {
    private String nic;
    private String paymentMethod;
    private int amount;
    private String paymentRadio;

    public Payment(String nic, String paymentMethod, int amount, String paymentRadio) {
        this.nic = nic;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentRadio = paymentRadio;
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

    @Override
    public String toString() {
        return "Payment{" +
                "nic='" + nic + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", amount=" + amount +
                ", paymentRadio='" + paymentRadio + '\'' +
                '}';
    }
}
