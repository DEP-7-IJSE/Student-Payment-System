package model;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String courseID;
    private double courseFee;
    private int studentCount;

    public Course(String courseID, double courseFee, int studentCount) {
        this.courseID = courseID;
        this.courseFee = courseFee;
        this.studentCount = studentCount;
    }

    public Course() {
    }

    public static Course fromManageCourseMap(String courseID, Map<String, String> data) {
        return new Course(
                courseID.replace("c#", ""),
                Double.parseDouble(data.get("fee")),
                Integer.parseInt(data.get("count"))
        );
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public Map<String, String> toManageCourseMap() {
        HashMap<String, String> courseMap = new HashMap<>();
        courseMap.put("fee", String.valueOf(courseFee));
        courseMap.put("count", String.valueOf(studentCount));
        return courseMap;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseFee=" + courseFee +
                ", studentCount=" + studentCount +
                '}';
    }
}
