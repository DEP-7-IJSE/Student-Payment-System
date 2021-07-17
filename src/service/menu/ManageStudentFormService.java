package service.menu;

import model.Student;
import model.tm.ManageStudentTM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageStudentFormService {
    private static final File studentDB = new File("student-db.dep7");
    private static List<Student> STUDENT_LIST = new ArrayList<>();

    static {
        readDataFromFile();
    }

    private static void readDataFromFile() {
        if (!studentDB.exists()) return;

        try (FileInputStream fosStudent = new FileInputStream(studentDB);
             ObjectInputStream oosStudent = new ObjectInputStream(fosStudent)) {

            STUDENT_LIST = (ArrayList<Student>) oosStudent.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
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

    public void removeStudent(ManageStudentTM tm) {
        for (Student student : STUDENT_LIST) {
            if (tm.getNIC().equals(student.getNic())) {
                STUDENT_LIST.remove(student);
                if (!writeDataFile()) STUDENT_LIST.add(student);
                break;
            }
        }
    }

    private boolean writeDataFile() {
        try (FileOutputStream fosStudent = new FileOutputStream(studentDB);
             ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent)) {

            oosStudent.writeObject(STUDENT_LIST);

        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
