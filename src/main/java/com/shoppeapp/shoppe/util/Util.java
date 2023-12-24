package com.shoppeapp.shoppe.util;

import com.shoppeapp.shoppe.user.User;
import com.shoppeapp.shoppe.user.UserController;
import com.shoppeapp.shoppe.user.UserRepository;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@NoArgsConstructor
public abstract class Util {
    
    @Setter
    @Getter
    protected static String USERNAME;

    @Setter
    @Getter
    protected static String PASSWORD;
    private static UserRepository userRepository;

    protected void playErrorSound() {
        String filePath = "src/main/resources/sounds/windows-error-sound-effect.mp3";

        MediaPlayer player = new MediaPlayer(new Media(Paths.get(filePath).toUri().toString()));
        player.play();
    }
    @Deprecated
    protected void showNotification(Stage stage, String message) {
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

    protected  <T extends Node> void switchScenes(String resource, String title, T sceneNode) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage stage = (Stage) sceneNode.getScene().getWindow();
        stage.setMaximized(true);
        stage.setTitle(title);
        stage.getIcons().add(new Image("/icons/main-form-icon.png"));
        stage.setScene(scene);
    }

    protected void showAlert(String title, String contentText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);

        ImageView imageView = new ImageView(new Image("icons/main-form-icon.png"));
        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        alert.showAndWait();

    }

    public void showPopUp(String message, Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
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

    protected ObservableList getObservableList(List items) {
        var observableList = FXCollections.observableArrayList();
        observableList.addAll(items);
        return observableList;
    }

    public static User setUsername() {
        if (userRepository != null) {
            Optional<User> optionalUser = userRepository.findUserByNameAndPassword(getUSERNAME(), getPASSWORD());
            return new User(optionalUser.map(User::getName).orElse("nope"));
        } else {
            return new User("new", "new", LocalDateTime.now());
        }
    }

    protected void selectInput(TextField textField) {
        textField.setOnMousePressed(mouseEvent -> textField.selectAll());
    }

    protected void allowNumericInput(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
                playErrorSound();
            }
        });

    }

    protected void allowTextInput(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                field.setText(newValue.replaceAll("[^a-zA-Z]", ""));
                playErrorSound();
            }
        });
    }

    protected <T> void scrollToLastItem(ObservableList<T> list, TableView tableView) {
        tableView.scrollTo(list.size() - 1);
    }

    protected void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure about closing the application?");

        ImageView imageView = new ImageView(new Image("icons/main-form-icon.png"));
        imageView.setFitHeight(45);
        imageView.setFitWidth(45);

        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnYes, btnNo);

        Optional<ButtonType> result  = alert.showAndWait();

        if (result.isPresent() && result.get() == btnYes) {
            System.exit(0);
        }
    }
    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }
}
