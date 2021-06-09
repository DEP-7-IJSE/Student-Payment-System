package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;

public class DashBoardFormController {
    public AnchorPane dashBoard;
    public TableColumn idCol;
    public TableColumn registrationCol;
    public TableColumn paymentCol;
    public TableColumn incomeCol;
    public JFXHamburger btnMenu;
    public JFXDrawer dwrSideMenu;
    public Label lblDate;
    public TableView tblDashBoard;
    public StackPane rootPane;

    public void initialize() {
        rootPane.setOpacity(0.5);
        makeFadeIn();
        try {
            Parent menuPane = FXMLLoader.load(this.getClass().getResource("/view/SideMenu.fxml"));
            dwrSideMenu.setSidePane(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            lblDate.getScene().getAccelerators().put(new KeyCharacterCombination("O", KeyCombination.CONTROL_ANY), () -> {
                try {
                    paymentOnAction(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        lblDate.setText(String.valueOf(LocalDate.now()));
        HamburgerBasicCloseTransition bt = new HamburgerBasicCloseTransition(btnMenu);
        bt.setRate(-1);
        btnMenu.setOnMouseClicked(e -> {
            bt.setRate(bt.getRate() * -1);
            bt.play();
            if (dwrSideMenu.isClosed()) {
                dwrSideMenu.open();
                dwrSideMenu.setVisible(true);
            } else {
                dwrSideMenu.close();
                dwrSideMenu.setVisible(false);
            }
        });
    }

    private void makeFadeIn() {
        FadeTransition ft = new FadeTransition(Duration.millis(500),rootPane);
        ft.setFromValue(0.5);
        ft.setToValue(1);
        ft.play();
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/PaymentForm.fxml"))));
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        ((Stage) lblDate.getScene().getWindow()).close();
        Stage login = new Stage();
        login.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"))));
        login.sizeToScene();
        login.setTitle("Login Form");
        login.setResizable(false);
        login.centerOnScreen();
        login.show();
    }
}
