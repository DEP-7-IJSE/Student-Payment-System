package service.impl.menu;

import map.Maps;
import model.Course;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageCourseServiceRedisImpl {

    private final Jedis client;

    public ManageCourseServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public void saveCourse(String type, int batch, double fee, int count) throws DuplicateEntryException {
        String courseID = type + batch;
        if (client.exists(courseID)) {
            throw new DuplicateEntryException();
        }
        client.hset(courseID, Maps.toManageCourseMap(fee, count));
    }

    public ArrayList<Course> getAll(String query) {
        ArrayList<Course> getCourse = new ArrayList<>();
        Set<String> data = client.keys("*");

        for (String course : data) {
            if (Character.isDigit(course.charAt(0))) continue;
            if (course.contains(query)) {
                getCourse.add(Maps.fromManageCourseMap(course, client.hgetAll(course)));
            } else {
                List<String> hvals = client.hvals(course);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        getCourse.add(Maps.fromManageCourseMap(course, client.hgetAll(course)));
                        break;
                    }
                }
            }
        }
        return getCourse;
    }

    public void deleteCourse(String id) {
        client.del(id);
    }
}
