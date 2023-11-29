package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
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
    public Pane passwordPanel;
    public Label txtRegister;
    public Pane user_panel;
    public static Stage stage;
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

    // TODO: 11/20/2023 Enable authentication
    private void openMainStage() {
        btnLogin.setOnAction(actionEvent -> {
//            initNameAndPassword();
//            authenticateUser(name, password);
            showSalesStage();
        });
    }

    private void changeVisibility() {
        txtRegister.setOnMouseClicked(mouseEvent -> {
            btnRegister.setVisible(true);
            passwordPanel.setVisible(true);
        });
    }

    private void showSalesStage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/windows/sales.fxml"));
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(parent));
        stage.getIcons().add(new Image("icons/main-form-icon.png"));
        stage.setMaximized(true);
        stage.setTitle("Sales");
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
            initNameAndPassword();
            registerUser(name, password, confirmPassword);
        });
    }

    private void initNameAndPassword() {
        name = txtLoginName.getText().trim().toLowerCase();
        password =  txtLoginPass.getText().trim();
        confirmPassword = txtLoginConfirmPass.getText().trim();
        Util.setUSERNAME(name);
        Util.setPASSWORD(password);
    }
    private void registerUser(String name, String password, String confirmPassword) {
        if (isUserAlreadyExist()) {
            showAlert("Information", "Username already taken, user another.", Alert.AlertType.INFORMATION);
        } else {
            if (name == null || password == null || confirmPassword == null) {
                showAlert("Information", "Please fill all fields.", Alert.AlertType.INFORMATION);
            } else if (!password.equals(confirmPassword)) {
                showAlert("Error", "Enter matching passwords please.", Alert.AlertType.ERROR);
            } else {
                userService.save(new User(name, password, LocalDateTime.now()));
                showSalesStage();
                Util.showPopUp("Registration successful", stage);
            }
        }
    }
    private void authenticateUser(String name, String password) {
        var result = userService.authenticateUser(name, password);
        if (result) {
            showSalesStage();
            Util.showPopUp("Login successful", stage);
        } else {
            Util.showAlert("Login", "Login failed, enter valid name and password.", Alert.AlertType.ERROR);
        }
    }
    private boolean isUserAlreadyExist() {
        return userService.isUserAlreadyExist(name);
    }

}
