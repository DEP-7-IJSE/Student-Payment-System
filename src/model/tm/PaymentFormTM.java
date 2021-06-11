package model.tm;

public class PaymentFormTM {
    private String courseID;
    private String nic;
    private int amount;

    public PaymentFormTM(String courseID, String nic, int amount) {
        this.courseID = courseID;
        this.nic = nic;
        this.amount = amount;
    }

    public PaymentFormTM() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentFormTM{" +
                "courseID='" + courseID + '\'' +
                ", nic='" + nic + '\'' +
                ", amount=" + amount +
                '}';
    }
}
