package com.shoppeapp.shoppe.user;

import com.shoppeapp.shoppe.mail.MailService;
import com.shoppeapp.shoppe.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@org.springframework.stereotype.Controller
@NoArgsConstructor(force = true)
public class UserController extends Util {

    public TextField txtLoginName;
    public ImageView loginImage;
    public ChoiceBox comboAction;
    public Pane user_panel;
    public Pane register_component_panel;
    public Pane login_component_panel;
    public Pane reset_component_panel;
    public PasswordField txtLoginPassword;
    public Button btnLogin;
    public TextField txtRegisterName;
    public PasswordField txtRegisterPass;
    public Button btnRegister;
    public PasswordField txtRegisterConfirmPass;
    public PasswordField txtRegisterEmail;
    public PasswordField txtRegPhoneNo;
    public Pane first_reset_component_pane;
    public TextField txtFirstResetName;
    public PasswordField txtFirstResetEmail;
    public Button btnFirstReset;
    public PasswordField txtConfirmReset;
    public Button btnReset;
    public PasswordField txtPasswordReset;
    private MailService mailService;

    @FXML
    public void initialize() {
        setPanelVisibility();
        setImages();
        populateCombo();
//        register();
        openMainStage();
        mailService = new MailService();
        displayOptions();
    }

    private void populateCombo() {
        comboAction.getItems().addAll(List.of("Login", "Register", "Reset password"));
    }

    private void displayOptions() {
        comboAction.setOnAction(actionEvent -> {
            var choice = comboAction.getValue().toString();
            switch (choice) {
                case "Login":
                    managePanelDisplay(login_component_panel, register_component_panel, reset_component_panel, first_reset_component_pane);
                    break;
                case "Register":
                    managePanelDisplay(register_component_panel, login_component_panel, reset_component_panel, first_reset_component_pane);
                    break;
                case "Reset password":
                    managePanelDisplay(first_reset_component_pane, register_component_panel, login_component_panel,  reset_component_panel);
                    break;
            }
        });
    }

    private void openMainStage() {
        btnLogin.setOnAction(actionEvent -> {
            login();
        });

        btnRegister.setOnAction(actionEvent -> {
            register();
        });

        btnFirstReset.setOnAction(actionEvent -> {
            checkUserAvailabilityAndSendCode();
        });

        btnReset.setOnAction(actionEvent -> {
            // TODO: 2/22/2024 To be implemented later
        });
    }

    private void navigateToSales() {
        switchScenes("/windows/sales.fxml", "Sales", btnLogin);
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

    private void login() {
        if (areLoginFieldsBlank()) {
            showAlert("Information", "Please fill all fields.", Alert.AlertType.INFORMATION);
        } else {
            var name = txtLoginName.getText().trim();
            var password = txtLoginPassword.getText().trim();
            Util.setUSERNAME(name);
            Util.setPASSWORD(password);
            authenticateUser(name, password);
        }
    }


    private boolean areLoginFieldsBlank() {
        return (txtLoginPassword.getText().isBlank() || txtLoginName.getText().isBlank());
    }

    private boolean areRegisterFieldsBlank() {
        return (txtRegisterName.getText().isBlank() ||
                txtRegisterPass.getText().isBlank() ||
                txtRegisterConfirmPass.getText().isBlank() ||
                txtRegisterEmail.getText().isBlank() ||
                txtRegPhoneNo.getText().isBlank());
    }

    private boolean areResetFieldsBlank() {
        return (txtPasswordReset.getText().isBlank() || txtConfirmReset.getText().isBlank());
    }

    private boolean areFirstRegFieldsBlank() {
        return (txtFirstResetEmail.getText().isBlank() || txtFirstResetName.getText().isBlank());
    }

    private void checkUserAvailabilityAndSendCode() {
        if (!areFirstRegFieldsBlank()) {
            var name = txtFirstResetName.getText().trim();
            var email = txtFirstResetEmail.getText().trim();
            if (UserService.isUserAlreadyExist(name)) {
                if (isValidEmail(email)) {
                    sendResetCode(email);
                    managePanelDisplay(reset_component_panel, register_component_panel, first_reset_component_pane, register_component_panel);
                } else {
                    invalidEmailError();
                }
            } else {
                showAlert("Information", "User does not exist", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Information", "Fill both fields.", Alert.AlertType.INFORMATION);
        }
    }

    @SneakyThrows
    private void sendResetCode(String email) {
        var random = new Random();
        var min = 100000;
        var max = 999999;
        var randomCode = random.nextInt(max - min + 1) + min;
        var subject = "Shoppe application password reset";
        var body = "This is your Shoppe application reset code: " + String.format("%06d", randomCode);
        var googleHost = "www.google.com";
        try {
            String inet4Address = Inet4Address.getByName(googleHost).getHostAddress();
            mailService.sendMail(email, subject, body);
            showAlert("Password Reset", "Check " + email + " for the reset code.", Alert.AlertType.INFORMATION);
            System.out.println(inet4Address);
        } catch (UnknownHostException exception) {
            showAlert("Network",
                    "Enable network connectivity to continue.", Alert.AlertType.INFORMATION);
        }
    }

    private void invalidEmailError() {
        showAlert("Error", "Please enter a valid mail address.", Alert.AlertType.ERROR);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void register() {
        if (!areRegisterFieldsBlank()) {
            var name = txtRegisterName.getText().trim();
            var password = txtRegisterPass.getText().trim();
            var confirmPassword = txtRegisterConfirmPass.getText().trim();
            var email = txtRegisterEmail.getText().trim();
            var phoneNo = txtRegPhoneNo.getText().trim();
            if (isUserAlreadyExist(name)) {
                showAlert("Information", "Username already taken, user another.", Alert.AlertType.INFORMATION);
            } else {
                if (isValidEmail(email)) {
                    if (password.contentEquals(confirmPassword)) {
                        UserService.save(new User(name, password, LocalDateTime.now(), email, phoneNo));
                        navigateToSales();
                    } else {
                        showAlert("Error", "Enter matching passwords please.", Alert.AlertType.ERROR);
                    }
                } else {
                    invalidEmailError();
                }
            }
        } else {
            showAlert("Registration", "Registration failed, fill fields with valid name and password.", Alert.AlertType.ERROR);
        }
    }

    private void authenticateUser(String name, String password) {
        var result = UserService.authenticateUser(name, password);
        if (result) {
            navigateToSales();
        } else {
            showAlert("Login", "Login failed, check the username and password or register to continue.", Alert.AlertType.ERROR);
        }
    }

    private boolean isUserAlreadyExist(String name) {
        return UserService.isUserAlreadyExist(name);
    }

    private void setPanelVisibility() {
        login_component_panel.setVisible(false);
        register_component_panel.setVisible(false);
        reset_component_panel.setVisible(false);
        first_reset_component_pane.setVisible(false);
    }

    private void managePanelDisplay(Pane main, Pane node1, Pane node2, Pane node3) {
        main.setVisible(true);
        node1.setVisible(false);
        node2.setVisible(false);
        node3.setVisible(false);
    }
}
