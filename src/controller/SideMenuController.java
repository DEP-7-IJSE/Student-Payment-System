package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.io.IOException;

public class SideMenuController {

    public StackPane sideMenuContainer;
    public AnchorPane sideMenuPane;
    public Label lblManageStudent;
    public Label lblManageCourse;
    public Label lblManagePayment;
    public Label lblManageUsers;
    public Label lblGetReport;

    public void initialize() {
        manageStudentClicked(null);
    }

    public void manageStudentClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManageStudentForm.fxml");
        changeColors(lblManageStudent);
    }
    public void manageCourseClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManageCourseForm.fxml");
        changeColors(lblManageCourse);
    }

    public void managePaymentClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManagePaymentForm.fxml");
        changeColors(lblManagePayment);
    }

    public void manageUsersClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManageUsersForm.fxml");
        changeColors(lblManageUsers);
    }

    public void getReportClicked(MouseEvent mouseEvent) {
        navigateMenuItem("GetReport.fxml");
        changeColors(lblGetReport);
    }

    private void navigateMenuItem(String location) {
        try {
            if (sideMenuPane.getScene() != null) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/menu/" + location));
                Parent root = loader.load();
                Scene scene = sideMenuContainer.getScene();
                root.translateXProperty().set(scene.getWidth());
                sideMenuContainer.getChildren().add(root);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            } else {
                sideMenuPane.getChildren().removeAll();
                sideMenuPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/menu/" + location)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeColors(Label lblSide) {
        lblSide.setBackground(new Background(new BackgroundFill(Color.valueOf("#576574"), new CornerRadii(10), null)));
        lblSide.setTextFill(Color.WHITE);
        Label[] allLabels = {lblGetReport, lblManageUsers, lblManagePayment, lblManageCourse, lblManageStudent};
        for (Label label : allLabels) {
            if (!label.equals(lblSide)) {
                label.setBackground(new Background(new BackgroundFill(Color.valueOf("#7f8c8d"), null, null)));
                label.setTextFill(Color.valueOf("#2c3e50"));
                /*label.setOnMouseEntered(event -> label.setTextFill(Color.WHITE));
                label.setOnMouseExited(event -> label.setTextFill(Color.valueOf("#2c3e50")));*/
            }
        }
    }
}
