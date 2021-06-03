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
        Stage login = (Stage) loginForm.getScene().getWindow();
        login.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/DashBoardForm.fxml"))));
        login.setMinWidth(1024);
        login.setMinHeight(768);
        login.setTitle("DashBoard");
        login.setResizable(true);
        login.centerOnScreen();
        login.show();
    }

    public void resetPaswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ResetPasswordForm.fxml"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
