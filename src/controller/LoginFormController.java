package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.impl.LoginFormServiceRedisImpl;

import java.io.IOException;

public class LoginFormController {
    private final LoginFormServiceRedisImpl LOGIN_FORM_SERVICE = new LoginFormServiceRedisImpl();
    public AnchorPane loginForm;
    public JFXComboBox cmbUsers;
    public JFXPasswordField txtPassword;

    public void initialize() {
        cmbUsers.getItems().addAll(LOGIN_FORM_SERVICE.getUsers());
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        if (!cmbUsers.getItems().isEmpty()) {
            String credentials = LOGIN_FORM_SERVICE.getCredentials(cmbUsers.getValue().toString());
            if (!txtPassword.getText().equals(credentials)) {
                new Alert(Alert.AlertType.ERROR, "Wrong Password", ButtonType.OK).show();
                txtPassword.requestFocus();
            } else {
                navigateToDashboard();
            }
        } else {
            navigateToDashboard();
        }
    }

    public void resetPasswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ResetPasswordForm.fxml"))));
        stage.setTitle("Reset Password");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void navigateToDashboard() throws IOException {
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
}
