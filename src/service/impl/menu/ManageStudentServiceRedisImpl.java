package service.impl.menu;

import model.tm.ManageStudentTM;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageStudentServiceRedisImpl {

    private final Jedis client;
    private static final String STUDENT_PREFIX = "s#";

    public ManageStudentServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public List<ManageStudentTM> getAllStudent(String query) {
        List<ManageStudentTM> tm = new ArrayList<>();
        Set<String> data = client.keys(STUDENT_PREFIX + "*");
        for (String nic : data) {
            if (nic.contains(query)) {
                tm.add(ManageStudentTM.fromMap(nic, client.hgetAll(nic)));
            } else {
                List<String> hvals = client.hvals(nic);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        tm.add(ManageStudentTM.fromMap(nic, client.hgetAll(nic)));
                        break;
                    }
                }
            }
        }
        /*for (Student student : STUDENT_LIST) {
            if(student.getNic().contains(query) || student.getName().contains(query) || student.getAddress().contains(query) ||
                    student.getContact().contains(query) || student.getEmail().contains(query) ||
                    student.getDescription().contains(query) || student.getCourseID().contains(query)) {
                tm.add(new ManageStudentTM(student.getCourseID(), student.getNic(), student.getName(), student.getContact(), student.getAddress(), student.getEmail()));
            }
        }*/
        return tm;
    }

    public void removeStudent(ManageStudentTM tm) {
        //client.
    }
}
