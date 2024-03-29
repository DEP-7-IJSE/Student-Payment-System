/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Course;
import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import service.exception.DuplicateEntryException;
import service.impl.PaymentFormServiceMYSQLImpl;
import service.impl.menu.GetReportServiceMYSQLImpl;
import service.impl.menu.ManageCourseServiceMYSQLImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static util.ValidationUtil.*;

public class PaymentFormController {

    private final PaymentFormServiceMYSQLImpl paymentFormService = new PaymentFormServiceMYSQLImpl();
    private final ManageCourseServiceMYSQLImpl manageCourseService = new ManageCourseServiceMYSQLImpl();
    private final GetReportServiceMYSQLImpl getReportService = new GetReportServiceMYSQLImpl();
    public ImageView imgBack;
    public Label lblDate;
    public JFXComboBox<String> cmbPaymentMethod;
    public StackPane secondPane;
    public TextField txtReceipt;
    public TextField txtnic;
    public TextField txtName;
    public JFXComboBox<String> cmbCourseID;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtDescription;
    public JFXRadioButton rdoRegistration;
    public JFXRadioButton rdoFullPayment;
    public JFXRadioButton rdoInstalment;
    public TextField txtAmount;
    public ToggleGroup whatFor;
    public TableView<PaymentFormTM> tblPayment;
    public Label lblTime;

    private int receiptNumber = getReportService.getLastReceiptNb() + 1;

    public PaymentFormController() throws SQLException {
    }

    public void initialize() throws SQLException {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadAllPayments();

        setCourseID();

        cmbCourseID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setAmount(rdoFullPayment, FeeType.COURSE_FEE);
            setAmount(rdoRegistration, FeeType.REGISTRATION_FEE);
        });

        whatFor.getToggles().get(0).selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) setAmount(rdoRegistration, FeeType.REGISTRATION_FEE);
        });

        whatFor.getToggles().get(1).selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) setAmount(rdoFullPayment, FeeType.COURSE_FEE);
        });

        whatFor.getToggles().get(2).selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) txtAmount.clear();
        });

        lblDate.setText(String.valueOf(LocalDate.now()));
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(900), event -> {
            String time = String.format("%tT", new Date());
            lblTime.setText(time);
        }));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();

        txtReceipt.setText(String.format("R%04d", receiptNumber));

        secondPane.setOpacity(0.5);
        makeFadeIn();
        Platform.runLater(() -> {
            lblDate.getScene().getAccelerators().put(new KeyCharacterCombination("b", KeyCombination.CONTROL_ANY), () -> {
                try {
                    imgBackClicked(null);
                } catch (IOException ignored) {

                }
            });
        });
        ObservableList<String> items = cmbPaymentMethod.getItems();
        items.add("Card Payment");
        items.add("Online Transfer");
        items.add("Cash");
    }

    private void setAmount(JFXRadioButton selected, FeeType feeType) {
        ArrayList<Course> all = null;
        try {
            all = manageCourseService.getAll(cmbCourseID.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Course course : all) {
            if (selected.isSelected()) {
                if (feeType == FeeType.COURSE_FEE) txtAmount.setText(String.valueOf(course.getCourseFee()));
                if (feeType == FeeType.REGISTRATION_FEE) txtAmount.setText(String.valueOf(course.getRegistrationFee()));
            }
        }
    }

    private void setCourseID() throws SQLException {
        ObservableList<String> courseID = cmbCourseID.getItems();
        List<String> allCourses = paymentFormService.getAllCourses();
        courseID.addAll(allCourses);
    }

    private void makeFadeIn() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), secondPane);
        ft.setFromValue(0.5);
        ft.setToValue(1);
        ft.play();
    }

    public void imgBackClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) secondPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/DashBoardForm.fxml"))));
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        imgBack.setImage(new Image("assets/Black Back.png"));
    }

    public void mouseExited(MouseEvent mouseEvent) {
        imgBack.setImage(new Image("assets/White Back.png"));
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) throws SQLException {
        String whatForPayment = "";
        if (rdoFullPayment.isSelected()) {
            whatForPayment = rdoFullPayment.getText();
        } else if (rdoRegistration.isSelected()) {
            whatForPayment = rdoRegistration.getText();
        } else {
            whatForPayment = rdoInstalment.getText();
        }
        try {

            if (!isValidated()) {
                return;
            }

            Student student = new Student(
                    txtnic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtEmail.getText(),
                    txtDescription.getText(),
                    cmbCourseID.getValue());
            Payment payment = new Payment(
                    txtReceipt.getText(),
                    txtnic.getText(),
                    cmbPaymentMethod.getValue(),
                    new BigDecimal(txtAmount.getText()),
                    whatForPayment,
                    lblDate.getText(),
                    System.getProperty("app.user"),
                    cmbCourseID.getValue()
            );
            boolean saved = paymentFormService.savePayments(student, payment);
            if (saved) {
                receiptNumber++;
                txtReceipt.setText(String.format("R%04d", receiptNumber));
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully", ButtonType.OK).show();
                clearForm();
                loadAllPayments();
            } else {
                new Alert(Alert.AlertType.ERROR, "Saved Failed").show();
            }
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "Duplication Entry", ButtonType.CLOSE).show();
            txtnic.requestFocus();
        }
    }

    private void loadAllPayments() throws SQLException {
        tblPayment.getItems().clear();
        ObservableList<PaymentFormTM> items = tblPayment.getItems();
        List<PaymentFormTM> all = paymentFormService.findAll();
        items.addAll(all);
    }

    private void clearForm() {
        txtnic.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtDescription.clear();
        txtnic.clear();
        txtAmount.clear();
    }

    private boolean isValidated() {
        String nic = txtnic.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String amount = txtAmount.getText();

        if (!isValidNIC(nic)) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC").show();
            txtnic.requestFocus();
            return false;
        } else if (!(isValidName(name)) && name.trim().length() >= 3) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name. Name should contain at least 3 letters and can contain only alphabetic letters and spaces").show();
            txtName.requestFocus();
            return false;
        } else if (!(address.trim().length() >= 4 && isValidAddress(address))) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address. Address should be at least 4 digits and can contain only alphabetic letters, spaces and - , . / \\").show();
            txtAddress.requestFocus();
            return false;
        } else if (!isValidContact(contact)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact Number").show();
            txtContact.requestFocus();
            return false;
        } else if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email. Email can contain only letters, digits, periods and underscore.").show();
            txtEmail.requestFocus();
            return false;

        } else if (!isValidAmount(amount)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Amount").show();
            txtAmount.requestFocus();
            return false;
        } else if (cmbPaymentMethod.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select the payment method").show();
            cmbPaymentMethod.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private enum FeeType {
        COURSE_FEE, REGISTRATION_FEE
    }
}
