package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.tm.ManagePaymentTM;
import model.tm.ManageStudentTM;
import service.menu.ManagePaymentService;

import java.util.ArrayList;

public class ManagePaymentFormController {

    public TableView<ManagePaymentTM> tblPayment;
    public JFXTextField txtCourseID;
    public JFXTextField txtStudent;
    public JFXTextField txtAmount;
    public JFXButton btnUpdate;
    public TextField txtSearch;

    private final ManagePaymentService MANAGE_PAYMENT_SERVICE=new ManagePaymentService();

    public void initialize(){
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
                    MANAGE_PAYMENT_SERVICE.remove(param.getValue());
                    tblPayment.getItems().remove(param.getValue());
                });
                return remove;
            }
        });

        loadAllPaymentDetails("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> loadAllPaymentDetails(newValue));

        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue != null){
                txtCourseID.setText(newValue.getCourseID());
                txtAmount.setText(String.valueOf(newValue.getAmount()));
                txtStudent.setText(newValue.getStudentNIC());
            }
        });
    }

    private void loadAllPaymentDetails(String query) {
        tblPayment.getItems().clear();
        for (ManagePaymentTM tm : MANAGE_PAYMENT_SERVICE.loadAllPayments(query)) {
            tblPayment.getItems().add(new ManagePaymentTM(tm.getDate(),tm.getCourseID(),tm.getStudentNIC(),tm.getAmount(),tm.getName()));
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        MANAGE_PAYMENT_SERVICE.loadPayment(tblPayment.getSelectionModel().selectedItemProperty().getValue().getStudentNIC(),
                txtCourseID.getText(),txtStudent.getText(),txtAmount.getText());
        loadAllPaymentDetails("");
        txtAmount.clear();
        txtCourseID.clear();
        txtStudent.clear();
    }
}