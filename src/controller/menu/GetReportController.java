/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller.menu;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.GetReportTM;
import service.impl.menu.GetReportServiceRedisImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetReportController {
    public Label lblMonth;
    private final GetReportServiceRedisImpl GET_REPORT_SERVICE = new GetReportServiceRedisImpl();
    public TableView<GetReportTM> tblReport;

    public void initialize() {
        tblReport.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("receiptNb"));
        tblReport.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblReport.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblReport.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblReport.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("payment"));
        tblReport.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("receivedBy"));

        tblReport.getItems().addAll(GET_REPORT_SERVICE.loadAllData());

        Date month = new Date();
        String format = new SimpleDateFormat("yyyy-MMMM").format(month);
        lblMonth.setText(format);
    }

}
