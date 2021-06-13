package controller.menu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Course;
import service.menu.ManageCourseService;

import java.util.ArrayList;

public class ManageCourseFormController {
    private final ManageCourseService MANAGE_COURSE_SERVICE = new ManageCourseService();
    public AnchorPane ManageCoursePane;
    public JFXComboBox<String> cmbProgramType;
    public JFXTextField txtBatchNb;
    public JFXTextField txtCourseFee;
    public JFXTextField txtStudentCount;
    public Label lblCourseId;
    public TextField txtSearch;
    public TableView<Course> tblCourses;
    public JFXButton btnSave;

    public void initialize() {
        tblCourses.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("CourseID"));
        tblCourses.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("courseFee"));
        tblCourses.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("studentCount"));
        TableColumn<Course, JFXButton> lastCol = (TableColumn<Course, JFXButton>) tblCourses.getColumns().get(3);
        lastCol.setCellValueFactory(param -> new ObservableValueBase<JFXButton>() {
            @Override
            public JFXButton getValue() {
                JFXButton remove = new JFXButton("Delete");
                remove.setOnAction(event -> {
                    MANAGE_COURSE_SERVICE.deleteCourse(param.getValue().getCourseID());
                    tblCourses.getItems().remove(param.getValue());
                });
                return remove;
            }
        });

        loadAll("");

        tblCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getCourseID().charAt(3) == 'P') {
                    txtBatchNb.setText(newValue.getCourseID().substring(3));
                } else {
                    txtBatchNb.setText(newValue.getCourseID().substring(4));
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
        if (btnSave.getText().equalsIgnoreCase("Save")) {
            MANAGE_COURSE_SERVICE.saveCourse(cmbProgramType.getValue(), Integer.parseInt(txtBatchNb.getText()), Integer.parseInt(txtCourseFee.getText()), Integer.parseInt(txtStudentCount.getText()));
            lblCourseId.setText(cmbProgramType.getValue() + txtBatchNb.getText());
            loadAll("");
            refreshForm();
        } else {

        }
    }

    private void loadAll(String query) {
        tblCourses.getItems().clear();
        ObservableList<Course> items = tblCourses.getItems();
        items.addAll(MANAGE_COURSE_SERVICE.getAll(query));
    }

    private void refreshForm() {
        txtCourseFee.clear();
        txtBatchNb.clear();
        txtStudentCount.clear();
    }
}
