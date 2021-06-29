package controller.menu;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.User;
import service.exception.DuplicateEntryException;
import service.impl.menu.ManageUserServiceRedisImpl;

import static util.ValidationUtil.isValidName;
import static util.ValidationUtil.isValidPassword;

public class ManageUserFormController {
    private final ManageUserServiceRedisImpl MANAGE_USER_SERVICE = new ManageUserServiceRedisImpl();
    public JFXRadioButton rdoAdmin;
    public JFXRadioButton rdoRegular;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtPasswordAgain;
    public TextField txtSearch;

    public void btnSave_OnAction(ActionEvent actionEvent) {

        try {
            if (!isValidated()) {
                return;
            }
            String userType = "";
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

        if (userName.trim().isEmpty() || !isValidName(userName)) {
            new Alert(Alert.AlertType.ERROR, "Invalid UserName").show();
            txtUserName.requestFocus();
            return false;
        } else if (!password.equals(passwordAgain) || !isValidPassword(password)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }
}
