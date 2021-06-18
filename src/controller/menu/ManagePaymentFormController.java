package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.ManagePaymentTM;
import service.exception.DuplicateEntryException;
import service.menu.ManagePaymentService;

import java.util.Optional;

import static util.ValidationUtil.*;

public class ManagePaymentFormController {

    private final ManagePaymentService MANAGE_PAYMENT_SERVICE = new ManagePaymentService();
    public TableView<ManagePaymentTM> tblPayment;
    public JFXTextField txtCourseID;
    public JFXTextField txtAmount;
    public JFXButton btnUpdate;
    public TextField txtSearch;

    public void initialize() {
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
                        MANAGE_PAYMENT_SERVICE.remove(param.getValue());
                        tblPayment.getItems().remove(param.getValue());
                    }
                });
                return remove;
            }
        });

        loadAllPaymentDetails("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> loadAllPaymentDetails(newValue));

        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCourseID.setText(newValue.getCourseID());
                txtAmount.setText(String.valueOf(newValue.getAmount()));
            }
        });
    }

    private void loadAllPaymentDetails(String query) {
        tblPayment.getItems().clear();
        tblPayment.getItems().addAll(MANAGE_PAYMENT_SERVICE.loadAllPayments(""));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if(!isValidated()){
                return;
            }

            MANAGE_PAYMENT_SERVICE.loadPayment(tblPayment.getSelectionModel().selectedItemProperty().getValue().getStudentNIC(),
                    txtCourseID.getText(), txtAmount.getText());
            loadAllPaymentDetails("");
            txtAmount.clear();
            txtCourseID.clear();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR,"Duplication CourseID",ButtonType.CLOSE).show();
            txtCourseID.requestFocus();
        }
    }

    private boolean isValidated(){
        String courseID = txtCourseID.getText();
        String amount = txtAmount.getText();

        if(!isValidCourseID(courseID)){
            new Alert(Alert.AlertType.ERROR, "Invalid CourseID").show();
            txtCourseID.requestFocus();
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
