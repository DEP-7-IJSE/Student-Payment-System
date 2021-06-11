package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.FadeTransition;
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
import service.menu.ManageCourseService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

    private int receiptNumber=1;

    public void initialize() {
        tblPayment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblPayment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblPayment.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("amount"));

        lblDate.setText(String.valueOf(LocalDate.now()));

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
        try{
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
                    whatFor.getSelectedToggle().selectedProperty().getName()
            );
            boolean saved = paymentFormService.savePayments(student, payment);
            if(saved) {
                receiptNumber++;
                txtReceipt.setText(String.format("R%04d",receiptNumber));
                new Alert(Alert.AlertType.INFORMATION, "Saved Sccessfully", ButtonType.OK).show();
                clearForm();
                loadAllPayments();
            }
        }catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR,"Error",ButtonType.CLOSE).show();
            clearForm();
        }
    }

    private void loadAllPayments() {
        ObservableList<PaymentFormTM> items = tblPayment.getItems();
        List<PaymentFormTM> all = paymentFormService.findAll();
        items.addAll(all);
    }

    public void newOnAction(ActionEvent actionEvent) {
        txtReceipt.setText(String.format("R%04d",receiptNumber));
        clearForm();
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
}
