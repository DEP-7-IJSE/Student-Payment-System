package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.tm.ManagePaymentTM;
import service.menu.ManagePaymentService;

import java.util.ArrayList;

public class ManagePaymentFormController {

    public TableView<ManagePaymentTM> tblPayment;
    public JFXTextField txtCourseID;
    public JFXTextField txtStudent;
    public JFXTextField txtAmount;
    public JFXButton btnUpdate;

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
        loadAllPaymentDetails();
    }

    private void loadAllPaymentDetails() {
        ArrayList<ManagePaymentTM> managePaymentTM = MANAGE_PAYMENT_SERVICE.loadAllPayments();
        tblPayment.getItems().addAll(managePaymentTM);
    }

}