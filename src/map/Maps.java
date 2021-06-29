package map;

import model.Course;
import model.tm.DashBoardTM;
import model.tm.ManagePaymentTM;
import model.tm.ManageStudentTM;
import model.tm.PaymentFormTM;

import java.util.HashMap;
import java.util.Map;

public class Maps {


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

    public static ManagePaymentTM fromManagePaymentMap(String nic, Map<String, String> data) {
        return new ManagePaymentTM(
                data.get("date"),
                data.get("courseID"),
                nic,
                Integer.parseInt(data.get("amount")),
                data.get("login")
        );
    }

    public static ManageStudentTM fromManageStudentMap(String nic, Map<String, String> data) {
        return new ManageStudentTM(
                data.get("courseID"),
                nic,
                data.get("name"),
                data.get("contact"),
                data.get("address"),
                data.get("email")
        );
    }
}
