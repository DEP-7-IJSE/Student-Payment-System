package controller.menu;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.User;
import service.exception.DuplicateEntryException;
import service.impl.menu.ManageUserServiceRedisImpl;

import java.util.List;
import java.util.Optional;

import static util.ValidationUtil.isValidPassword;
import static util.ValidationUtil.isValidUser;

public class ManageUserFormController {
    private final ManageUserServiceRedisImpl MANAGE_USER_SERVICE = new ManageUserServiceRedisImpl();
    public JFXRadioButton rdoAdmin;
    public JFXRadioButton rdoRegular;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtPasswordAgain;
    public TextField txtSearch;
    public JFXListView<String> lstUser;
    public JFXButton btnDelete;

    private String user;

    public void initialize() {
        loadAll("");

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAll(newValue);
        });


        lstUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setVisible(true);
            user = newValue;
        });
    }

    private void loadAll(String query) {
        lstUser.getItems().clear();
        List<String> user = MANAGE_USER_SERVICE.getUser(query);
        lstUser.getItems().addAll(user);
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        try {
            if (!isValidated()) {
                return;
            }
            String userType;
            if (rdoAdmin.isSelected()) {
                userType = "Admin";
            } else {
                userType = "Regular";
            }

            User user = new User(
                    userType,
                    txtUserName.getText(),
                    txtPassword.getText()
            );
            MANAGE_USER_SERVICE.saveUser(user);
            loadAll("");
            txtUserName.clear();
            txtPassword.clear();
            txtPasswordAgain.clear();
            new Alert(Alert.AlertType.INFORMATION, "Saved").show();
        } catch (DuplicateEntryException e) {
            new Alert(Alert.AlertType.ERROR, "The userName is exists").show();
            txtUserName.requestFocus();
        }
    }

    private boolean isValidated() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String passwordAgain = txtPasswordAgain.getText();

        if (userName.trim().isEmpty() || !isValidUser(userName)) {
            new Alert(Alert.AlertType.ERROR, "Invalid UserName").show();
            txtUserName.requestFocus();
            return false;
        } else if (!isValidPassword(password)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            txtPassword.requestFocus();
            return false;
        } else if (!password.equals(passwordAgain)) {
            new Alert(Alert.AlertType.ERROR, "Password doesn't match").show();
            txtPasswordAgain.requestFocus();
            return false;
        }
        return true;
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Select the user", ButtonType.CLOSE).show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are sure you want to delete this user? \n" + user, ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                MANAGE_USER_SERVICE.deleteUser(user);
                loadAll("");
            }
        }
    }
}
