package com.shoppeapp.shoppe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {

    private final UserService userService;

    public Controller(UserService userService) {
        this.userService = userService;
    }

    public Pane panel;
    public TextArea txt;
    public Button btn;
    public TextField get;


    @FXML
    public void initialize() {
        getUserById();
    }

    private void getUserById() {
//        btn.setOnAction(actionEvent -> {
//            Optional<User> byId = userService.findById(Long.parseLong(get.getText()));
//            byId.ifPresent(user -> txt.setText(user.getDepartment() +
//                    user.getId() + user.getName()));
//            byId.ifPresent(System.out::println);
        btn.setOnAction(actionEvent -> {
            userService.findById(1L).ifPresent(user -> {
                System.out.println(user.getName());
                System.out.println("Tapped");
            });
        });


    }


}
