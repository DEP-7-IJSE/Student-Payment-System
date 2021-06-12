package model.tm;

public class ManageStudentTM {

    private String courseID;
    private String NIC;
    private String Name;
    private String contact;
    private String address;
    private String email;

    public ManageStudentTM(String courseID, String NIC, String name, String contact, String address, String email) {
        this.courseID = courseID;
        this.NIC = NIC;
        Name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public ManageStudentTM() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ManageStudentTM{" +
                "courseID='" + courseID + '\'' +
                ", NIC='" + NIC + '\'' +
                ", Name='" + Name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
