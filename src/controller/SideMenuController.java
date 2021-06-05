package controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class SideMenuController {

    public StackPane sideMenuContainer;
    public AnchorPane sideMenuPane;

    public void initialize() {
        manageCourseClicked(null);
    }

    public void manageCourseClicked(MouseEvent mouseEvent){
        navigateMenuItem("ManageCourseForm.fxml");
    }

    public void managePaymentClicked(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../view/menu/ManagePaymentForm.fxml"));
            Scene scene = sideMenuContainer.getScene();
            root.translateYProperty().set(scene.getHeight());
            sideMenuContainer.getChildren().add(root);
            Timeline timeline= new Timeline();
            KeyValue kv = new KeyValue(root.translateYProperty(),0, Interpolator.EASE_IN);
            KeyFrame kf=new KeyFrame(Duration.seconds(1),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event -> sideMenuContainer.getChildren().remove(sideMenuPane));
            timeline.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //navigateMenuItem("ManagePaymentForm.fxml");
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
