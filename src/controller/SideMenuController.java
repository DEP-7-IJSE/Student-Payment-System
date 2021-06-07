package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
    public Label lblGetReport;

    public void initialize() {
        manageCourseClicked(null);
        //changeColors();
    }

    public void manageCourseClicked(MouseEvent mouseEvent){
        navigateMenuItem("ManageCourseForm.fxml");
        changeColors(lblManageCourse,lblManageUsers,lblManagePayment,lblGetReport,lblManageStudent);
    }

    public void managePaymentClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManagePaymentForm.fxml");
        changeColors(lblManagePayment,lblManageCourse,lblManageUsers,lblGetReport,lblManageStudent);
    }

    public void manageUsersClicked(MouseEvent mouseEvent) {
        navigateMenuItem("ManageUsersForm.fxml");
        changeColors(lblManageUsers,lblManagePayment,lblManageCourse,lblGetReport,lblManageStudent);
    }

    public void getReportClicked(MouseEvent mouseEvent) {
        navigateMenuItem("GetReport.fxml");
        changeColors(lblGetReport,lblManageUsers,lblManagePayment,lblManageCourse,lblManageStudent);
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

    private void changeColors(Label lblSide, Label... lblIdle){
        lblSide.setBackground(new Background(new BackgroundFill(Color.valueOf("#576574"),null,null)));
        for (Label label : lblIdle) {
            label.setBackground(new Background(new BackgroundFill(Color.valueOf("#7f8c8d"),null,null)));
        }
    }
}
