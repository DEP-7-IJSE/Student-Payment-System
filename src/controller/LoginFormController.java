package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import service.exception.DuplicateEntryException;
import service.impl.LoginFormServiceRedisImpl;
import service.impl.menu.ManageUserServiceRedisImpl;

import java.io.IOException;

public class LoginFormController {
    private final LoginFormServiceRedisImpl LOGIN_FORM_SERVICE = new LoginFormServiceRedisImpl();
    private final ManageUserServiceRedisImpl USER_FORM_SERVICE = new ManageUserServiceRedisImpl();
    public AnchorPane loginForm;
    public JFXPasswordField txtPassword;
    public JFXTextField txtUser;

    public void initialize() {
        if (LOGIN_FORM_SERVICE.getUsers().isEmpty()) {
            User user = new User(
                    "Admin",
                    "admin",
                    "admin"
            );
            try {
                USER_FORM_SERVICE.saveUser(user);
            } catch (DuplicateEntryException ignored) {

            }
        }
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUser.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Enter the user", ButtonType.OK).show();
            return;
        }

        if (!LOGIN_FORM_SERVICE.authentication(txtUser.getText(), txtPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Wrong Password", ButtonType.OK).show();
            txtPassword.clear();
            txtPassword.requestFocus();
        } else {
            System.setProperty("app.user", txtUser.getText());
            System.setProperty("app.userType", LOGIN_FORM_SERVICE.getUserType(txtUser.getText()));
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
