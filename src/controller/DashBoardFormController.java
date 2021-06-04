package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    public void initialize(){
        try {
            Parent menuPane = FXMLLoader.load(this.getClass().getResource("/view/SideMenu.fxml"));
            dwrSideMenu.setSidePane(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblDate.setText(String.valueOf(LocalDate.now()));
        HamburgerBasicCloseTransition bt = new HamburgerBasicCloseTransition(btnMenu);
        bt.setRate(-1);
        btnMenu.setOnMouseClicked(e ->{
            bt.setRate(bt.getRate()*-1);
            bt.play();
            if(dwrSideMenu.isClosed()){
                dwrSideMenu.open();
            }else{
                dwrSideMenu.close();
            }
        });
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        Stage pay = (Stage) lblDate.getScene().getWindow();
        pay.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/PaymentForm.fxml"))));
    }
}
