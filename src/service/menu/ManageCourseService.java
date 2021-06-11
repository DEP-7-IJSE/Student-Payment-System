package service.menu;

import model.Course;

import java.util.ArrayList;

public class ManageCourseService {
    ArrayList<Course> list= new ArrayList();

    public void saveCourse(String type, int batch, int fee, int count){
        String courseID=type+batch;
        Course course= new Course(courseID, fee, count);
        list.add(course);
    }

    public ArrayList<Course> getAll(){
        return list;
    }

    public void deleteCourse(String id){
        list.removeIf(course -> id.equals(course.getCourseID()));
    }
}
