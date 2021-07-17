/*
 * Copyright (c) 2021 - present Pethum Jeewantha. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VerifyFormController {
    public AnchorPane verify;

    public void verifyOnAction(ActionEvent actionEvent) {
        Stage window = (Stage) verify.getScene().getWindow();
        window.close();
        Stage owner = (Stage) window.getOwner();
        owner.close();
    }
}
