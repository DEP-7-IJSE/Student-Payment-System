package controller.menu;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.tm.ManageStudentTM;
import service.menu.ManageStudentFormService;

import java.util.List;
import java.util.Optional;

public class ManageStudentFormController {

    public TextField txtSearch;
    public TableView<ManageStudentTM> tblStudent;
    public ListView<String> lstStudent;
    public JFXButton btnDelete;
    public JFXButton btnSave;

    private final ManageStudentFormService MANAGE_STUDENT_FORM_SERVICE = new ManageStudentFormService();

    public void initialize(){
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("NIC"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("email"));

        getAllStudents("");

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            lstStudent.getItems().clear();
            btnDelete.setVisible(true);
            btnSave.setVisible(true);
            if(newValue != null){
                lstStudent.getItems().add(newValue.getCourseID());
                lstStudent.getItems().add(newValue.getNIC());
                lstStudent.getItems().add(newValue.getName());
                lstStudent.getItems().add(newValue.getContact());
                lstStudent.getItems().add(newValue.getAddress());
                lstStudent.getItems().add(newValue.getEmail());
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> getAllStudents(newValue));
    }

    private void getAllStudents(String query) {
        tblStudent.getItems().clear();
        for (ManageStudentTM tm : MANAGE_STUDENT_FORM_SERVICE.getAllStudent(query)) {
            tblStudent.getItems().add(new ManageStudentTM(tm.getCourseID(),tm.getNIC(),tm.getName(),tm.getContact(),tm.getAddress(),tm.getEmail()));
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are sure you want to delete this?", ButtonType.YES, ButtonType.NO).showAndWait();
        if(buttonType.get() == ButtonType.YES) {
            MANAGE_STUDENT_FORM_SERVICE.removeStudent(tblStudent.getSelectionModel().getSelectedItem());
            getAllStudents("");
            lstStudent.getItems().clear();
            btnDelete.setVisible(false);
            btnSave.setVisible(false);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
