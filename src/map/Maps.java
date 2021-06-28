package map;

import model.Course;
import model.Payment;
import model.Student;
import model.tm.DashBoardTM;
import model.tm.PaymentFormTM;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    public static Map<String, String> toMap(Student student, Payment payment) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", student.getName());
        map.put("address", student.getAddress());
        map.put("contact", student.getContact());
        map.put("email", student.getEmail());
        map.put("description", student.getDescription());
        map.put("courseID", student.getCourseID());
        map.put("paymentMethod", payment.getPaymentMethod());
        map.put("amount", String.valueOf(payment.getAmount()));
        map.put("what", payment.getPaymentRadio());
        map.put("date", payment.getDate());
        map.put("login", payment.getLogin());
        return map;
    }


    public static PaymentFormTM fromPaymentMap(String nic, Map<String, String> data) {
        return new PaymentFormTM(
                nic,
                data.get("courseID"),
                Integer.parseInt(data.get("amount"))
        );
    }

    public static DashBoardTM fromDashBoardMap(Map<String, String> data) {
        return new DashBoardTM(
                data.get("courseID"),
                data.get("what"),
                data.get("amount"),
                data.get("login")
        );
    }

    public static Map<String, String> toManageCourseMap(double fee, int count) {
        HashMap<String, String> courseMap = new HashMap<>();
        courseMap.put("fee", String.valueOf(fee));
        courseMap.put("count", String.valueOf(count));
        return courseMap;
    }

    public static Course fromManageCourseMap(String courseID, Map<String, String> data) {
        return new Course(
                courseID,
                Double.parseDouble(data.get("fee")),
                Integer.parseInt(data.get("count"))
        );
    }
}
