package service.impl.menu;

import model.Course;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageCourseServiceRedisImpl {

    private static final String COURSE_PREFIX = "c#";
    private final Jedis client;

    public ManageCourseServiceRedisImpl() {
        client = new Jedis("localhost", 9090);
    }

    public void saveCourse(Course course) throws DuplicateEntryException {
        if (client.exists(course.getCourseID())) {
            throw new DuplicateEntryException();
        }
        client.hset(COURSE_PREFIX + course.getCourseID(), course.toManageCourseMap());
    }

    public ArrayList<Course> getAll(String query) {
        ArrayList<Course> getCourse = new ArrayList<>();
        Set<String> data = client.keys(COURSE_PREFIX + "*");

        for (String course : data) {
            if (course.contains(query)) {
                getCourse.add(Course.fromManageCourseMap(course, client.hgetAll(course)));
            } else {
                List<String> hvals = client.hvals(course);
                for (String hval : hvals) {
                    if (hval.contains(query)) {
                        getCourse.add(Course.fromManageCourseMap(course, client.hgetAll(course)));
                        break;
                    }
                }
            }
        }
        return getCourse;
    }

    public void deleteCourse(String id) {
        client.del(COURSE_PREFIX + id);
    }
}
