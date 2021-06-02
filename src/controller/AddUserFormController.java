package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserFormController {
    public AnchorPane addForm;

    public void addUserOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/VerifyForm.fxml"))));
        stage.initOwner(addForm.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
