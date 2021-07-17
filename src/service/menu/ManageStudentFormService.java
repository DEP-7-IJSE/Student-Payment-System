/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.menu;

import model.Student;
import model.tm.ManageStudentTM;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentFormService {
    private static final List<Student> STUDENT_LIST = new ArrayList<>();

    static{
        Student s1=new Student("505460068v","Pethum","Panadura","011-444044","dffcews@gmail.com","","DEP7");
        Student s2=new Student("505443068v","Jeewantha","Panadura","011-445044","dffcws@gmail.com","","DEP8");
        Student s3=new Student("505456068v","Kalhara","Panadura","011-444544","dff5ews@gmail.com","","GDSE7");
        Student s4=new Student("505476068v","Kavindu","Panadura","011-444644","dfcews@gmail.com","","DEP10");

        STUDENT_LIST.add(s1);
        STUDENT_LIST.add(s2);
        STUDENT_LIST.add(s3);
        STUDENT_LIST.add(s4);
    }

    public List<ManageStudentTM> getAllStudent(String query){
        List<ManageStudentTM> tm = new ArrayList<>();
        for (Student student : STUDENT_LIST) {
            if(student.getNic().contains(query) || student.getName().contains(query) || student.getAddress().contains(query) ||
                    student.getContact().contains(query) || student.getEmail().contains(query) ||
                    student.getDescription().contains(query) || student.getCourseID().contains(query)) {
                tm.add(new ManageStudentTM(student.getCourseID(), student.getNic(), student.getName(), student.getContact(), student.getAddress(), student.getEmail()));
            }
        }
        return tm;
    }

    public void removeStudent(ManageStudentTM tm){
        for (Student student : STUDENT_LIST) {
            if(tm.getNIC().equals(student.getNic())){
                STUDENT_LIST.remove(student);
                break;
            }
        }
    }
}
