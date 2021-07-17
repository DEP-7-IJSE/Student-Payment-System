package service.menu;

import model.Course;
import service.exception.DuplicateEntryException;

import java.io.*;
import java.util.ArrayList;

public class ManageCourseService {
    private static final File courseDB = new File("course-db.dep7");

    private static ArrayList<Course> list = new ArrayList();

    static {
        readDataFromFile();
    }

    private static void readDataFromFile() {
        if (!courseDB.exists()) return;

        try (FileInputStream fosStudent = new FileInputStream(courseDB);
             ObjectInputStream oosStudent = new ObjectInputStream(fosStudent)) {

            list = (ArrayList<Course>) oosStudent.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCourse(String type, int batch, double fee, int count) throws DuplicateEntryException {
        String courseID = type + batch;
        for (Course course : list) {
            if (course.getCourseID().equals(courseID)) {
                throw new DuplicateEntryException();
            }
        }
        Course courseTM = new Course(courseID, fee, count);
        list.add(courseTM);
        boolean saved = writeDataFile();
        if (!saved) list.remove(courseTM);
    }

    public ArrayList<Course> getAll(String query) {
        ArrayList<Course> getCourse = new ArrayList<>();
        for (Course course : list) {
            if (course.getCourseID().contains(query) || String.valueOf(course.getCourseFee()).contains(query)
                    || String.valueOf(course.getStudentCount()).contains(query)) {
                getCourse.add(course);
            }
        }
        return getCourse;
    }

    public void deleteCourse(Course course) {
        list.remove(course);
        if (!writeDataFile()) list.add(course);
    }

    private boolean writeDataFile() {
        try (FileOutputStream fosStudent = new FileOutputStream(courseDB);
             ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent)) {

            oosStudent.writeObject(list);

        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
