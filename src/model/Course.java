package model;

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

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseFee=" + courseFee +
                ", studentCount=" + studentCount +
                '}';
    }
}
