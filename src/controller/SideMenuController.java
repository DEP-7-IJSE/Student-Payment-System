package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SideMenuController {
    public AnchorPane sideMenuPane;

    public void initialize() {
        manageCourseClicked(null);
    }

    public void manageCourseClicked(MouseEvent mouseEvent){
        navigateMenuItem("ManageCourseForm.fxml");
    }

    public void managePaymentClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManagePaymentForm.fxml");
    }

    public void manageUsersClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManageUsersForm.fxml");
    }

    public void getReportClicked(MouseEvent mouseEvent) {
        navigateMenuItem("GetReport.fxml");
    }

    private void navigateMenuItem(String location) {
        try {
            sideMenuPane.getChildren().removeAll();
            sideMenuPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/menu/"+location)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
