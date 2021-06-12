package controller.menu;

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
    public ListView lstStudent;

    private final ManageStudentFormService MANAGE_STUDENT_FORM_SERVICE = new ManageStudentFormService();

    public void initialize(){
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courseID"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("NIC"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("email"));

        getAllStudents();
    }

    private void getAllStudents() {
        List<ManageStudentTM> allStudent = MANAGE_STUDENT_FORM_SERVICE.getAllStudent();
        tblStudent.getItems().addAll(allStudent);
    }
}
