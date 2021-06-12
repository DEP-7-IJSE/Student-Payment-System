package controller.menu;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.tm.ManageStudentTM;
import service.menu.ManageStudentFormService;

import java.util.List;

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

        getAllStudents();

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
    }

    private void getAllStudents() {
        List<ManageStudentTM> allStudent = MANAGE_STUDENT_FORM_SERVICE.getAllStudent();
        tblStudent.getItems().addAll(allStudent);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        MANAGE_STUDENT_FORM_SERVICE.removeStudent(tblStudent.getSelectionModel().getSelectedItem());
        tblStudent.getItems().clear();
        getAllStudents();
        lstStudent.getItems().clear();
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }
}
