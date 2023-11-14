package com.shoppeapp.shoppe.util;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Util {
    public static void showNotification(Stage stage, String message) {
        Label label = new Label(message);
        label.setStyle("-fx-background-color: lightgray; -fx-padding: 10px;");
        label.setTextFill(Color.BLACK);

        StackPane root = (StackPane) stage.getScene().getRoot();
        root.getChildren().add(label);

        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> root.getChildren().remove(label));
        delay.play();
    }
    public static void showAlert(String title, String contentText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
