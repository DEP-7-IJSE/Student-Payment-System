/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.GetReportTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.impl2.menu.GetReportServiceMYSQLImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GetReportController {
    public Label lblMonth;
    private final GetReportServiceMYSQLImpl GET_REPORT_SERVICE = new GetReportServiceMYSQLImpl();
    public TableView<GetReportTM> tblReport;

    public void initialize() throws SQLException {
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

    public void getReportOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/ijse-monthlyReport.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        HashMap<String, Object> param = new HashMap<>();
        param.put("date", lblMonth.getText());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JRBeanCollectionDataSource(tblReport.getItems()));
        JasperViewer.viewReport(jasperPrint, false);
    }
}
