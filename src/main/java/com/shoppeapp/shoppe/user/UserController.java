package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@org.springframework.stereotype.Controller
@NoArgsConstructor(force = true)
public class UserController extends Util {

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
        setImages();
        manageVisibility();
        register();
        openMainStage();
        changeVisibility();
    }

    // TODO: 11/20/2023 Enable authentication
    private void openMainStage() {
        btnLogin.setOnAction(actionEvent -> {
//            initNameAndPassword();
//            authenticateUser(name, password);
            navigateToSales();
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
                navigateToSales();
                showPopUp("Registration successful", btnLogin);
            }
        }
    }
    private void authenticateUser(String name, String password) {
        var result = userService.authenticateUser(name, password);
        if (result) {
            navigateToSales();
            showPopUp("Login successful", btnLogin);
        } else {
            showAlert("Login", "Login failed, enter valid name and password.", Alert.AlertType.ERROR);
        }
    }
    private boolean isUserAlreadyExist() {
        return userService.isUserAlreadyExist(name);
    }

}
