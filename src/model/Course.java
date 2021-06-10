package model;

public class Course {
    private String programType;
    private int batchNumber;
    private int courseFee;
    private int studentCount;

    public Course(String programType, int batchNumber, int courseFee, int studentCount) {
        this.programType = programType;
        this.batchNumber = batchNumber;
        this.courseFee = courseFee;
        this.studentCount = studentCount;
    }

    public Course() {
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(int courseFee) {
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
                "programType='" + programType + '\'' +
                ", batchNumber=" + batchNumber +
                ", courseFee=" + courseFee +
                ", studentCount=" + studentCount +
                '}';
    }
}
