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

import static util.ValidationUtil.*;

public class ManageUserFormController {
    private final ManageUserServiceRedisImpl MANAGE_USER_SERVICE = new ManageUserServiceRedisImpl();
    public JFXRadioButton rdoAdmin;
    public JFXRadioButton rdoRegular;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXPasswordField txtPasswordAgain;
    public TextField txtSearch;
    public JFXTextField txtContact;

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
                    txtContact.getText(),
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
        String contact = txtContact.getText();
        String password = txtPassword.getText();
        String passwordAgain = txtPasswordAgain.getText();

        if (userName.trim().isEmpty() || !isValidName(userName)) { //Todo: change validation to username
            new Alert(Alert.AlertType.ERROR, "Invalid UserName").show();
            txtUserName.requestFocus();
            return false;
        } else if (contact.trim().isEmpty() || !isValidContact(contact)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact").show();
            txtContact.requestFocus();
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
}
