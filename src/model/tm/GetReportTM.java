/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model.tm;

import java.math.BigDecimal;
import java.util.Map;

public class GetReportTM {
    private String receiptNb;
    private String date;
    private String nic;
    private String courseID;
    private BigDecimal payment;
    private String receivedBy;

    public GetReportTM() {
    }

    public GetReportTM(String receiptNb, String date, String nic, String courseID, BigDecimal payment, String receivedBy) {
        this.receiptNb = receiptNb;
        this.date = date;
        this.nic = nic;
        this.courseID = courseID;
        this.payment = payment;
        this.receivedBy = receivedBy;
    }

    public static GetReportTM fromMap(String nic, Map<String, String> data) {
        return new GetReportTM(
                data.get("receiptNb"),
                data.get("date"),
                nic.replace("p#", ""),
                data.get("courseID"),
                new BigDecimal(data.get("amount")),
                data.get("login")
        );
    }

    public String getReceiptNb() {
        return receiptNb;
    }

    public void setReceiptNb(String receiptNb) {
        this.receiptNb = receiptNb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    @Override
    public String toString() {
        return "GetReportTM{" +
                "receiptNb='" + receiptNb + '\'' +
                ", date='" + date + '\'' +
                ", nic=" + nic +
                ", courseID='" + courseID + '\'' +
                ", payment=" + payment +
                ", receivedBy='" + receivedBy + '\'' +
                '}';
    }
}
