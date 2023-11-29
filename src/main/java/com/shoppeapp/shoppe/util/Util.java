package com.shoppeapp.shoppe.util;

import com.shoppeapp.shoppe.user.User;
import com.shoppeapp.shoppe.user.UserRepository;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

public class Util {
    
    @Setter
    @Getter
    public static String USERNAME;

    @Setter
    @Getter
    private static String PASSWORD;
    private static UserRepository userRepository;

    public Util(UserRepository userRepository) {
        Util.userRepository = userRepository;
    }

    @Deprecated
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

        ImageView imageView = new ImageView(new Image("icons/main-form-icon.png"));
        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        VBox vBox = new VBox(imageView);
//        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();

    }

    public static void showPopUp(String message, Stage stage) {
        Popup popup = new Popup();
        BorderPane borderPane = new BorderPane();
        Label label = new Label(message);
        label.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        borderPane.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
        borderPane.setCenter(label);
        popup.getContent().add(borderPane);
        double midX = stage.getX() + stage.getWidth() / 2;
        double midY = stage.getY() + stage.getHeight() / 2;
        popup.show(stage, midX - label.getWidth() / 2, midY + stage.getHeight() / 2);
        PauseTransition transition = new PauseTransition(Duration.seconds(4));
        transition.setOnFinished(actionEvent -> popup.hide());
        transition.play();

    }

    public static User setUsername() {
        if (userRepository != null) {
            Optional<User> optionalUser = userRepository.findUserByNameAndPassword(getUSERNAME(), getPASSWORD());
            return new User(optionalUser.map(User::getName).orElse("nope"));
        } else {
            return new User("new", "new", LocalDateTime.now());
        }
    }

    public static void selectInput(TextField textField) {
        textField.setOnMousePressed(mouseEvent -> textField.selectAll());
    }

    public static void allowNumericInput(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
                playErrorSound();
            }
        });

    }

    public static void allowTextInput(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                field.setText(newValue.replaceAll("[^a-zA-Z]", ""));
                playErrorSound();
            }
        });
    }
    public static void playErrorSound() {
        String filePath = "src/main/resources/sounds/windows-error-sound-effect.mp3";

        MediaPlayer player = new MediaPlayer(new Media(Paths.get(filePath).toUri().toString()));
        player.play();
    }
}
