/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Course {
    private String courseID;
    private BigDecimal registrationFee;
    private BigDecimal courseFee;
    private int studentCount;

    public Course(String courseID, BigDecimal registrationFee, BigDecimal courseFee, int studentCount) {
        this.courseID = courseID;
        this.registrationFee = registrationFee;
        this.courseFee = courseFee;
        this.studentCount = studentCount;
    }

    public Course() {
    }

    public static Course fromMap(String courseID, Map<String, String> data) {
        return new Course(
                courseID.replace("c#", ""),
                new BigDecimal(data.get("Registration")),
                new BigDecimal(data.get("fee")),
                Integer.parseInt(data.get("count"))
        );
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public BigDecimal getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(BigDecimal registrationFee) {
        this.registrationFee = registrationFee;
    }

    public BigDecimal getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(BigDecimal courseFee) {
        this.courseFee = courseFee;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> courseMap = new HashMap<>();
        courseMap.put("Registration", String.valueOf(registrationFee));
        courseMap.put("fee", String.valueOf(courseFee));
        courseMap.put("count", String.valueOf(studentCount));
        return courseMap;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", registrationFee=" + registrationFee +
                ", courseFee=" + courseFee +
                ", studentCount=" + studentCount +
                '}';
    }
}
