package com.youlu.ui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PopWindow
{
    public static void alert(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, new ButtonType[0]);
                alert.show();
            }
        });
    }
}
