/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package service.impl.menu;

import model.Course;
import service.exception.DuplicateEntryException;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class ManageCourseServiceMYSQLImpl {

    private Connection connection;
    private PreparedStatement saveCourse;
    private PreparedStatement getCourseIds;
    private PreparedStatement updateCourses;
    private PreparedStatement deleteCourses;

    public ManageCourseServiceMYSQLImpl() {
        connection = DBConnection.getInstance().getConnection();
        try {
            saveCourse = connection.prepareStatement("INSERT INTO course " +
                    "(course_id, registration_fee, course_fee, student_count) VALUES (?,?,?,?);");
            getCourseIds = connection.prepareStatement("SELECT course_id FROM course WHERE course_id=?;");
            updateCourses = connection.prepareStatement("UPDATE course SET course_fee=?, student_count=?, registration_fee=? WHERE course_id=?;");
            deleteCourses = connection.prepareStatement("DELETE FROM course WHERE course_id=?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveCourse(Course course) throws DuplicateEntryException, SQLException {
        saveCourse.setString(1, course.getCourseID());
        saveCourse.setDouble(2, course.getRegistrationFee().doubleValue());
        saveCourse.setDouble(3, course.getCourseFee().doubleValue());
        saveCourse.setInt(4, course.getStudentCount());

        getCourseIds.setString(1, course.getCourseID());
        if (getCourseIds.executeQuery().next()) {
            throw new DuplicateEntryException();
        }

        return saveCourse.executeUpdate() == 1;
    }

    public boolean updateCourse(Course course) throws SQLException {
        updateCourses.setDouble(1, course.getCourseFee().doubleValue());
        updateCourses.setInt(2, course.getStudentCount());
        updateCourses.setDouble(3, course.getRegistrationFee().doubleValue());
        updateCourses.setString(4, course.getCourseID());

        return updateCourses.executeUpdate() == 1;
    }

    public ArrayList<Course> getAll(String query) throws SQLException {
        ArrayList<Course> getCourse = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE course_id LIKE '%QUERY%' OR registration_fee LIKE '%QUERY%' OR " +
                "course_fee LIKE '%QUERY%' OR student_count LIKE '%QUERY%';";
        sql = sql.replaceAll("QUERY", query);
        Statement getCourses = connection.createStatement();
        ResultSet rst = getCourses.executeQuery(sql);
        while (rst.next()) {
            getCourse.add(new Course(
                    rst.getString("course_id"),
                    rst.getBigDecimal("registration_fee"),
                    rst.getBigDecimal("course_fee"),
                    rst.getInt("student_count")
            ));
        }
        return getCourse;
    }

    public boolean deleteCourse(String id) throws SQLException {
        deleteCourses.setString(1, id);
        return deleteCourses.executeUpdate() == 1;
    }
}
