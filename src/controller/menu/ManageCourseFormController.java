package controller.menu;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Course;
import service.ManageCourseService;

import java.util.ArrayList;

public class ManageCourseFormController {
    public AnchorPane ManageCoursePane;
    public JFXComboBox<String> cmbProgramType;
    public JFXTextField txtBatchNb;
    public JFXTextField txtCourseFee;
    public JFXTextField txtStudentCount;
    public Label lblCourseId;
    public JFXListView lstCourses;

    public void initialize(){
        ObservableList<String> items = cmbProgramType.getItems();
        items.add("CMJD");
        items.add("DEP");
        items.add("GDSE");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        ManageCourseService manageCourseService= new ManageCourseService();
        manageCourseService.saveCourse(cmbProgramType.getValue(), Integer.parseInt(txtBatchNb.getText()), Integer.parseInt(txtCourseFee.getText()),Integer.parseInt(txtStudentCount.getText()));
        lblCourseId.setText(cmbProgramType.getValue()+txtBatchNb.getText());
    }
}
