package service.menu;

import model.Course;
import service.exception.DuplicateEntryException;

import java.util.ArrayList;

public class ManageCourseService {
    private static final ArrayList<Course> list= new ArrayList();

    static {
        Course c1 = new Course("DEP7",45000,25);
        Course c2 = new Course("DEP6",40000,20);
        Course c3 = new Course("DEP9",45000,25);
        Course c4 = new Course("DEP8",60000,30);

        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
    }

    public void saveCourse(String type, int batch, double fee, int count) throws DuplicateEntryException {
        String courseID=type+batch;
        for (Course course : list) {
            if(course.getCourseID().equals(courseID)){
                throw new DuplicateEntryException();
            }
        }
        Course courseTM = new Course(courseID, fee, count);
        list.add(courseTM);
    }

    public ArrayList<Course> getAll(String query){
        ArrayList<Course> getCourse = new ArrayList<>();
        for (Course course : list) {
            if(course.getCourseID().contains(query) || String.valueOf(course.getCourseFee()).contains(query)
            || String.valueOf(course.getStudentCount()).contains(query)){
                getCourse.add(course);
            }
        }
        return getCourse;
    }

    public void deleteCourse(String id){
        list.removeIf(course -> id.equals(course.getCourseID()));
    }
}
