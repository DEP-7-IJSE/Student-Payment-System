/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.ManagePaymentTM;
import service.exception.NotFoundException;
import service.impl.menu.ManagePaymentServiceMYSQLImpl;

import java.sql.SQLException;
import java.util.Optional;

import static util.ValidationUtil.isValidAmount;
import static util.ValidationUtil.isValidCourseID;

public class ManagePaymentFormController {

    private final ManagePaymentServiceMYSQLImpl MANAGE_PAYMENT_SERVICE = new ManagePaymentServiceMYSQLImpl();
    public TableView<ManagePaymentTM> tblPayment;
    public JFXTextField txtCourseID;
    public JFXTextField txtAmount;
    public JFXButton btnUpdate;
    public TextField txtSearch;

    public void initialize() throws SQLException {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("studentNIC"));
        tblPayment.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("amount"));
        tblPayment.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ManagePaymentTM, JFXButton> lastCol = (TableColumn<ManagePaymentTM, JFXButton>) tblPayment.getColumns().get(5);
        lastCol.setCellValueFactory(param -> new ObservableValueBase<JFXButton>() {
            @Override
            public JFXButton getValue() {
                JFXButton remove = new JFXButton("Delete");
                remove.setOnAction(event -> {
                    Optional<ButtonType> buttonType = new Alert(Alert.AlertType.WARNING, "Are you sure you want to update this? \n[It can't be recover again]",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        try {
                            if (MANAGE_PAYMENT_SERVICE.remove(param.getValue())) {
                                new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Deletion Failed").show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        tblPayment.getItems().remove(param.getValue());
                    }
                });
                return remove;
            }
        });

        loadAllPaymentDetails("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadAllPaymentDetails(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCourseID.setText(newValue.getCourseID());
                txtAmount.setText(String.valueOf(newValue.getAmount()));
            }
        });
    }

    private void loadAllPaymentDetails(String query) throws SQLException {
        tblPayment.getItems().clear();
        tblPayment.getItems().addAll(MANAGE_PAYMENT_SERVICE.loadAllPayments(query));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        if (!isValidated()) {
            return;
        }

        try {
            boolean updated = MANAGE_PAYMENT_SERVICE.updatePayment(tblPayment.getSelectionModel().selectedItemProperty().getValue().getStudentNIC(),
                    txtCourseID.getText(), txtAmount.getText());
            if (updated) new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
            if (!updated) new Alert(Alert.AlertType.ERROR, "Wrong Entered", ButtonType.CLOSE).show();
            loadAllPaymentDetails("");
            txtAmount.clear();
            txtCourseID.clear();
        } catch (NotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Not a valid courseID", ButtonType.CLOSE).show();
            txtCourseID.requestFocus();
        }
    }

    private boolean isValidated() {
        String courseID = txtCourseID.getText();
        String amount = txtAmount.getText();

        if (!isValidCourseID(courseID)) {
            new Alert(Alert.AlertType.ERROR, "Invalid CourseID").show();
            txtCourseID.requestFocus();
            return false;
        } else if (!isValidAmount(amount)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Amount").show();
            txtAmount.requestFocus();
            return false;
        } else {
            return true;
        }

    }
}
