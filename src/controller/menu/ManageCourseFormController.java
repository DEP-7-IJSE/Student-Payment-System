/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Course;
import service.exception.DuplicateEntryException;
import service.impl2.menu.ManageCourseServiceMYSQLImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static util.ValidationUtil.*;

public class ManageCourseFormController {
    private final ManageCourseServiceMYSQLImpl MANAGE_COURSE_SERVICE = new ManageCourseServiceMYSQLImpl();
    public AnchorPane ManageCoursePane;
    public JFXComboBox<String> cmbProgramType;
    public JFXTextField txtBatchNb;
    public JFXTextField txtCourseFee;
    public JFXTextField txtStudentCount;
    public Label lblCourseId;
    public TextField txtSearch;
    public TableView<Course> tblCourses;
    public JFXButton btnSave;
    public JFXTextField txtRegistrationFee;

    public void initialize() {
        tblCourses.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CourseID"));
        tblCourses.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("registrationFee"));
        tblCourses.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("courseFee"));
        tblCourses.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        TableColumn<Course, JFXButton> lastCol = (TableColumn<Course, JFXButton>) tblCourses.getColumns().get(4);
        lastCol.setCellValueFactory(param -> new ObservableValueBase<JFXButton>() {
            @Override
            public JFXButton getValue() {
                JFXButton remove = new JFXButton("Delete");
                remove.setOnAction(event -> {
                    Optional<ButtonType> confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this contact?", ButtonType.YES, ButtonType.NO).showAndWait();
                    if (confirmation.get().equals(ButtonType.YES)) {
                        try {
                            if (MANAGE_COURSE_SERVICE.deleteCourse(param.getValue().getCourseID())) {
                                new Alert(Alert.AlertType.INFORMATION, "deleted").show();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Deletion Failed").show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, "Deletion Failed").show();
                            e.printStackTrace();
                        }
                        tblCourses.getItems().remove(param.getValue());
                    }
                });
                return remove;
            }
        });

        loadAll("");

        tblCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getCourseID().charAt(2) == 'P') {
                    txtBatchNb.setText(newValue.getCourseID().substring(3));
                    cmbProgramType.setValue(newValue.getCourseID().substring(0, 3));
                } else {
                    txtBatchNb.setText(newValue.getCourseID().substring(4));
                    cmbProgramType.setValue(newValue.getCourseID().substring(0, 4));
                }
                txtCourseFee.setText(String.valueOf(newValue.getCourseFee()));
                txtStudentCount.setText(String.valueOf(newValue.getStudentCount()));
                btnSave.setText("Update");
            }
        });

        ObservableList<String> items = cmbProgramType.getItems();
        items.add("CMJD");
        items.add("DEP");
        items.add("GDSE");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> loadAll(newValue));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!isValidated()) {
            return;
        }
        try {
            if (btnSave.getText().equalsIgnoreCase("Save")) {

                Course course = new Course(
                        cmbProgramType.getValue() + txtBatchNb.getText(),
                        new BigDecimal(txtRegistrationFee.getText()),
                        new BigDecimal(txtCourseFee.getText()),
                        Integer.parseInt(txtStudentCount.getText())
                );

                if (MANAGE_COURSE_SERVICE.saveCourse(course)) {
                    lblCourseId.setText(cmbProgramType.getValue() + txtBatchNb.getText());
                    new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).show();
                    loadAll("");
                    refreshForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Course didn't saved", ButtonType.CLOSE).show();
                }

            } else {

                Course course = new Course(
                        cmbProgramType.getValue() + txtBatchNb.getText(),
                        new BigDecimal(txtRegistrationFee.getText()),
                        new BigDecimal(txtCourseFee.getText()),
                        Integer.parseInt(txtStudentCount.getText())
                );

                if (MANAGE_COURSE_SERVICE.updateCourse(course)) {
                    new Alert(Alert.AlertType.INFORMATION, "Saved", ButtonType.OK).show();
                    loadAll("");
                    refreshForm();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Course didn't saved", ButtonType.CLOSE).show();
                }
            }
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Duplication courses", ButtonType.CLOSE).show();
            txtBatchNb.requestFocus();
        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR, "Course didn't saved", ButtonType.CLOSE).show();
        }
    }

    private void loadAll(String query) {
        tblCourses.getItems().clear();
        ObservableList<Course> items = tblCourses.getItems();
        try {
            items.addAll(MANAGE_COURSE_SERVICE.getAll(query));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshForm() {
        txtCourseFee.clear();
        txtBatchNb.clear();
        txtStudentCount.clear();
        txtRegistrationFee.clear();
    }

    private boolean isValidated() {
        String batch = txtBatchNb.getText();
        String registration = txtRegistrationFee.getText();
        String fee = txtCourseFee.getText();
        String count = txtStudentCount.getText();

        if (batch.trim().isEmpty() || !isValidBatchNb(batch)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Batch Number").show();
            txtBatchNb.requestFocus();
            return false;
        } else if (!(isValidAmount(fee) || isValidAmount(registration))) {
            new Alert(Alert.AlertType.ERROR, "Invalid Fee").show();
            if (!isValidAmount(fee)) txtCourseFee.requestFocus();
            if (!isValidAmount(registration)) txtRegistrationFee.requestFocus();
            return false;
        } else if (!isValidStudentCount(count)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Student Count").show();
            txtStudentCount.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
