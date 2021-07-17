/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package model.tm;

import java.util.Map;

public class DashBoardTM {
    private String courseID;
    private String paidFor;
    private String paidAmount;
    private String receivedBy;

    public DashBoardTM(String courseID, String paidFor, String paidAmount, String receivedBy) {
        this.courseID = courseID;
        this.paidFor = paidFor;
        this.paidAmount = paidAmount;
        this.receivedBy = receivedBy;
    }

    public DashBoardTM() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(String paidFor) {
        this.paidFor = paidFor;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public static DashBoardTM fromMap(Map<String, String> data) {
        return new DashBoardTM(
                data.get("courseID"),
                data.get("what"),
                data.get("amount"),
                data.get("login")
        );
    }

    @Override
    public String toString() {
        return "DashBoardTM{" +
                "courseID='" + courseID + '\'' +
                ", paidFor='" + paidFor + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", receivedBy='" + receivedBy + '\'' +
                '}';
    }
}
