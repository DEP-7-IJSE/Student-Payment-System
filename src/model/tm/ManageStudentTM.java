/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model.tm;

import java.util.Map;

public class ManageStudentTM {

    private String courseID;
    private String NIC;
    private String name;
    private String contact;
    private String address;
    private String email;

    public ManageStudentTM(String courseID, String NIC, String name, String contact, String address, String email) {
        this.courseID = courseID;
        this.NIC = NIC;
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static ManageStudentTM fromMap(String nic, Map<String, String> data) {
        return new ManageStudentTM(
                data.get("courseID"),
                nic.replace("s#", ""),
                data.get("name"),
                data.get("contact"),
                data.get("address"),
                data.get("email")
        );
    }

    @Override
    public String toString() {
        return "ManageStudentTM{" +
                "courseID='" + courseID + '\'' +
                ", NIC='" + NIC + '\'' +
                ", Name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
