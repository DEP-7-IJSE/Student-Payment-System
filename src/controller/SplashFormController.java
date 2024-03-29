/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.DBConnection;

import java.io.IOException;

public class SplashFormController {
    public JFXProgressBar pgb;

    public void initialize() {
        pgb.setProgress(0);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (pgb.getProgress() <= 1) {
                pgb.setProgress(pgb.getProgress() + 0.01);
            }
        }));

        tl.setRate(3);
        tl.setCycleCount(Animation.INDEFINITE);
        tl.playFromStart();

        pgb.progressProperty().addListener((observable, oldValue, newValue) -> {
            try {

                if (newValue.intValue() == 1) {
                    tl.stop();
                    initializeUI();
                } else if (newValue.doubleValue() >= 0.95) {
                    DBConnection.getInstance().getConnection();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load the app, please contact DEPPO!").showAndWait();
                System.exit(1);
            }
        });
    }

    private void initializeUI() throws IOException {
        Stage primaryStage = (Stage) pgb.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml")));
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
