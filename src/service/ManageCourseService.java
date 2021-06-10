package service;

import model.Course;

import java.util.ArrayList;

public class ManageCourseService {
    ArrayList<Course> list= new ArrayList();

    public void saveCourse(String type, int batch, int fee, int count){
        Course course= new Course(type, batch, fee, count);
        list.add(course);
    }

    public ArrayList<Course> getAll(){
        return list;
    }
}
