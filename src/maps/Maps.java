package maps;

import model.Payment;
import model.Student;
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


    public static PaymentFormTM fromMap(String nic, Map<String, String> data) {
        return new PaymentFormTM(
                nic,
                data.get("courseID"),
                Integer.parseInt(data.get("amount"))
        );
    }
}
