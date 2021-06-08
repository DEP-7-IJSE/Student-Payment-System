package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginForm;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/DashBoardForm.fxml"))));
        stage.setMinWidth(1024);
        stage.setMinHeight(768);
        stage.setTitle("DashBoard");
        stage.setResizable(true);
        stage.centerOnScreen();
        ((Stage) loginForm.getScene().getWindow()).close();
        stage.show();
    }

    public void resetPaswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ResetPasswordForm.fxml"))));
        stage.setTitle("Reset Password");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
