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
import model.Payment;
import model.Student;
import model.tm.PaymentFormTM;
import service.PaymentFormService;
import service.exception.DuplicateEntryException;
import service.menu.ManageCourseService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static util.ValidationUtil.*;

public class PaymentFormController {

    private final PaymentFormService paymentFormService = new PaymentFormService();
    private final ManageCourseService manageCourseService = new ManageCourseService();
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

    private int receiptNumber=1;

    public void initialize() {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));

        lblDate.setText(String.valueOf(LocalDate.now()));
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(900), event -> {
            String time = String.format("%tT", new Date());
            lblTime.setText(time);
        }));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();

        txtReceipt.setText(String.format("R%04d",receiptNumber));

        setCourseID();

        secondPane.setOpacity(0.5);
        makeFadeIn();
        Platform.runLater(() -> {
            lblDate.getScene().getAccelerators().put(new KeyCharacterCombination("b", KeyCombination.CONTROL_ANY), () -> {
                try {
                    imgBackClicked(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        ObservableList<String> items = cmbPaymentMethod.getItems();
        items.add("Card Payment");
        items.add("Online Transfer");
        items.add("Cash");
    }

    private void setCourseID() {
        ObservableList<String> courseID = cmbCourseID.getItems();
        /*ArrayList<CourseTM> all = manageCourseService.getAll();
        for (CourseTM course : all) {
            courseID.add(course.getCourseID());
        }*/
        courseID.add("DEP7");
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

    public void btnSubmitOnAction(ActionEvent actionEvent) {
        String whatForPayment="";
        if(rdoFullPayment.isSelected()){
            whatForPayment=rdoFullPayment.getText();
        }else if(rdoRegistration.isSelected()){
            whatForPayment=rdoRegistration.getText();
        }else{
            whatForPayment=rdoInstalment.getText();
        }
        try{

            if(!isValidated()){
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
                    txtnic.getText(),
                    cmbPaymentMethod.getValue(),
                    Integer.parseInt(txtAmount.getText()),
                    whatForPayment,
                    lblDate.getText(),
                    "Logged"
            );
            System.out.println(whatFor.selectedToggleProperty().getName());
            boolean saved = paymentFormService.savePayments(student, payment);
            if(saved) {
                receiptNumber++;
                txtReceipt.setText(String.format("R%04d",receiptNumber));
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully", ButtonType.OK).show();
                clearForm();
                loadAllPayments();
            }
        }catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR,"Duplication Entry",ButtonType.CLOSE).show();
            txtnic.requestFocus();
        }
    }

    private void loadAllPayments() {
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

    private  boolean isValidated(){
        String nic = txtnic.getText();
        String name = txtName.getText();
        String address= txtAddress.getText();
        String contact= txtContact.getText();
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
        }else if (!isValidContact(contact)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact Number").show();
            txtContact.requestFocus();
            return false;
        } else if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email. Email can contain only letters, digits, periods and underscore.").show();
            txtEmail.requestFocus();
            return false;

        } else if(!isValidAmount(amount)){
            new Alert(Alert.AlertType.ERROR, "Invalid Amount").show();
            txtAmount.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
