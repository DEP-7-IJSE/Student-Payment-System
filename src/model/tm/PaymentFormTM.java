/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model.tm;

import java.util.Map;

public class PaymentFormTM {
    private String courseID;
    private String nic;
    private int amount;

    public PaymentFormTM(String courseID, String nic, int amount) {
        this.courseID = courseID;
        this.nic = nic;
        this.amount = amount;
    }

    public PaymentFormTM() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static PaymentFormTM fromMap(String nic, Map<String, String> data) {
        return new PaymentFormTM(
                data.get("courseID"),
                nic.replace("p#", ""),
                Integer.parseInt(data.get("amount"))
        );
    }

    @Override
    public String toString() {
        return "PaymentFormTM{" +
                "courseID='" + courseID + '\'' +
                ", nic='" + nic + '\'' +
                ", amount=" + amount +
                '}';
    }
}
