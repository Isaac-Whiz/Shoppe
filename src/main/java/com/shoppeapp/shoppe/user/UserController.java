package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@org.springframework.stereotype.Controller
@NoArgsConstructor(force = true)
public class UserController extends Util {

    public Button btnLogin;
    public TextField txtLoginName;
    public PasswordField txtLoginPass;
    public PasswordField txtLoginConfirmPass;
    public ImageView loginImage;
    public Button btnRegister;
    public Pane passwordPanel;
    public Label txtRegister;
    public Pane user_panel;
    private String name;
    private String password;


    @FXML
    public void initialize() {
        setImages();
        manageVisibility();
        register();
        openMainStage();
        changeVisibility();
    }

    private void openMainStage() {
        btnLogin.setOnAction(actionEvent -> {
            initNameAndPassword();
            authenticateUser(name, password);
        });
    }

    private void changeVisibility() {
        txtRegister.setOnMouseClicked(mouseEvent -> {
            btnRegister.setVisible(true);
            passwordPanel.setVisible(true);
        });
    }

    private void navigateToSales() {
        switchScenes("/windows/sales.fxml", "Sales", btnLogin);
    }

    private void manageVisibility() {
        passwordPanel.setVisible(false);
        btnRegister.setVisible(false);
    }

    private void setImages() {
        loginImage.setImage(new Image("/icons/user.png"));
        user_panel.setBackground(
                new Background(new BackgroundImage(new Image("/icons/welcome.jpg")
                        , BackgroundRepeat.NO_REPEAT,
                        null,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)));
    }

    private void initNameAndPassword() {
        if (!areFieldsBlank()) {
            name = txtLoginName.getText().trim().toLowerCase();
            password = txtLoginPass.getText().trim();
            Util.setUSERNAME(name);
            Util.setPASSWORD(password);
        } else {
            showAlert("Information", "Please fill all fields.", Alert.AlertType.INFORMATION);
        }
    }

    private void  register() {
        btnRegister.setOnAction(actionEvent -> {
            initNameAndPassword();
            registerUser(name, password);
        });
    }

    private boolean areFieldsBlankForReg() {
        return (areFieldsBlank()&& txtLoginConfirmPass.getText() == null);
    }

    private boolean areFieldsBlank() {
        return (txtLoginPass.getText() == null
                && txtLoginName.getText() == null);
    }

    private void registerUser(String name, String password) {
        if (isUserAlreadyExist()) {
            showAlert("Information", "Username already taken, user another.", Alert.AlertType.INFORMATION);
        } else {
            if (!areFieldsBlankForReg()) {
                String confirmPassword = txtLoginConfirmPass.getText().trim();
                if (!password.equals(confirmPassword)) {
                    showAlert("Error", "Enter matching passwords please.", Alert.AlertType.ERROR);
                } else {
                    UserService.save(new User(name, password, LocalDateTime.now()));
                    navigateToSales();
                }
            } else {
                showAlert("Registration", "Registration failed, fill fields with a valid name and a password.", Alert.AlertType.ERROR);
            }
        }
    }
    private void authenticateUser(String name, String password) {
        var result = UserService.authenticateUser(name, password);
        if (!areFieldsBlank() && result) {
                navigateToSales();
            } else {
                showAlert("Login", "Login failed, fill fields with a valid name and a password.", Alert.AlertType.ERROR);
            }
    }
    private boolean isUserAlreadyExist() {
        return UserService.isUserAlreadyExist(name);
    }

}
