package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.shoppeapp.shoppe.util.Util.showAlert;

@org.springframework.stereotype.Controller
public class UserController {
    private final UserService userService;
    public Button btnLogin;
    public TextField txtLoginName;
    public PasswordField txtLoginPass;
    public ImageView loginImage;
    public PasswordField txtLoginConfirmPass;
    public Button btnRegister;
    public Pane authentication_panel;
    public Pane passwordPanel;
    public Label txtRegister;
    private Stage stage;
    private String name;
    private String password;
    private String confirmPassword;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void initialize() {
        loginImage.setImage(new Image("/icons/user.png"));
        init();
        register();
        openMainStage();
        changeVisibility();
    }

    private void openMainStage() {
        btnLogin.setOnAction(actionEvent -> {
            authenticateUser(name, password);
        });
    }

    private void authenticateUser(String name, String password) {
        var result = userService.authenticateUser(name, password);
        if (result.isPresent()) {
            showMainStage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login failed");
            alert.setTitle("Login");
            alert.showAndWait();
        }
    }
    private void changeVisibility() {
        txtRegister.setOnMouseClicked(mouseEvent -> {
            btnRegister.setVisible(true);
            passwordPanel.setVisible(true);
        });
    }

    private void showMainStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/windows/main.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(parent));
        stage.getIcons().add(new Image("icons/main-form-icon.png"));
        stage.setMaximized(true);
        stage.setTitle("Main");
        stage.show();
        Window window = btnLogin.getScene().getWindow();
        window.hide();
    }

    private void init() {
        stage = new Stage();
        passwordPanel.setVisible(false);
        btnRegister.setVisible(false);
    }

    private void  register() {
        btnRegister.setOnAction(actionEvent -> {
            name = txtLoginName.getText().trim();
            password =  txtLoginPass.getText().trim();
            confirmPassword = txtLoginConfirmPass.getText().trim();
            registerUser(name, password, confirmPassword);
        });
    }


    private void registerUser(String name, String password, String confirmPassword) {
        if (name == null || password == null || confirmPassword == null) {
            showAlert("Information", "Please fill all fields", Alert.AlertType.INFORMATION);
        } else if (!password.equals(confirmPassword)) {
            showAlert("Error", "Enter matching passwords please.", Alert.AlertType.ERROR);
        } else {
            userService.save(new User(name, password, LocalDateTime.now()));
            showMainStage();
//            Util.showNotification(stage, "Registration successful");
        }
    }


}
