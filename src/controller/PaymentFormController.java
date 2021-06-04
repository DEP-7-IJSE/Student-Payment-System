package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PaymentFormController {

    public ImageView imgBack;
    public Label lblDate;

    public void initialize(){
        lblDate.setText(String.valueOf(LocalDate.now()));
        Platform.runLater(()->{
            lblDate.getScene().getAccelerators().put(new KeyCharacterCombination("b", KeyCombination.CONTROL_ANY), () -> {
                try {
                    imgBackClicked(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void imgBackClicked(MouseEvent mouseEvent) throws IOException {
        Stage back = (Stage) imgBack.getScene().getWindow();
        back.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/DashBoardForm.fxml"))));
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        imgBack.setImage(new Image("assets/Black Back.png"));
    }

    public void mouseExited(MouseEvent mouseEvent) {
        imgBack.setImage(new Image("assets/White Back.png"));
    }
}
