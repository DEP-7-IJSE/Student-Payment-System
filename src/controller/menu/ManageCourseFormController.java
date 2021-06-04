package controller.menu;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ManageCourseFormController {
    public AnchorPane ManageCoursePane;
    public JFXComboBox<String> cmbProgramType;
    public JFXTextField txtBatchNb;
    public JFXTextField txtCourseFee;
    public JFXTextField txtStudentCount;
    public Label lblCourseId;

    public void initialize(){
        ObservableList<String> items = cmbProgramType.getItems();
        items.add("CMJD");
        items.add("DEP");
        items.add("GDSE");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        lblCourseId.setText(cmbProgramType.getSelectionModel().getSelectedItem()+txtBatchNb.getText());

    }
}
