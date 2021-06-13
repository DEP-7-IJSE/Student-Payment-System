package model.tm;

public class DashBoardTM {
    private String courseID;
    private String paidFor;
    private String paidAmount;
    private String receivedBy;

    public DashBoardTM(String courseID, String paidFor, String paidAmount, String receivedBy) {
        this.courseID = courseID;
        this.paidFor = paidFor;
        this.paidAmount = paidAmount;
        this.receivedBy = receivedBy;
    }

    public DashBoardTM() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(String paidFor) {
        this.paidFor = paidFor;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    @Override
    public String toString() {
        return "DashBoardTM{" +
                "courseID='" + courseID + '\'' +
                ", paidFor='" + paidFor + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", receivedBy='" + receivedBy + '\'' +
                '}';
    }
}
