package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class SideMenuController {

    public StackPane sideMenuContainer;
    public AnchorPane sideMenuPane;
    public Label lblManageStudent;
    public Label lblManageCourse;
    public Label lblManagePayment;
    public Label lblManageUsers;

    public void initialize() {
        manageCourseClicked(null);
        changeColors();
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
            if (sideMenuPane.getScene()!=null) {
                Parent root = FXMLLoader.load(this.getClass().getResource("../view/menu/"+location));
                Scene scene = sideMenuContainer.getScene();
                root.translateXProperty().set(scene.getWidth());
                sideMenuContainer.getChildren().add(root);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            }else{
                sideMenuPane.getChildren().removeAll();
                sideMenuPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/menu/"+location)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeColors(){
        /*Canvas canvas = new Canvas(lblManageStudent.getPrefWidth(), lblManageStudent.getPrefHeight());
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        lblManageStudent.*/
        lblManageStudent.setOnMouseClicked(event -> {
            lblManageCourse.setBackground(new Background(new BackgroundFill(Color.valueOf("#7f8c8d"),null,null)));
            lblManageUsers.setBackground(new Background(new BackgroundFill(Color.valueOf("#7f8c8d"),null,null)));
            lblManagePayment.setBackground(new Background(new BackgroundFill(Color.valueOf("#7f8c8d"),null,null)));
            lblManageStudent.setBackground(new Background(new BackgroundFill(Color.valueOf("#576574"),null,null)));
        });
    }
}
