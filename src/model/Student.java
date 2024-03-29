/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String description;
    private String courseID;

    public Student(String nic, String name, String address, String contact, String email, String description, String courseID) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.description = description;
        this.courseID = courseID;
    }

    public Student() {
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("address", address);
        map.put("contact", contact);
        map.put("email", email);
        map.put("description", description);
        map.put("courseID", courseID);
        return map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", courseID='" + courseID + '\'' +
                '}';
    }
}
