package com.shoppeapp.shoppe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class ShoppeApplication extends Application {

    public static Stage exportedStage;
    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        applicationContext = SpringApplication.run(ShoppeApplication.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/user.fxml"));
    loader.setControllerFactory(applicationContext::getBean);
    Parent parent = loader.load();

        stage.setTitle("Shoppe");
        stage.setMaximized(true);
        stage.getIcons().add(new Image("icons/main-form-icon.png"));
        stage.setScene(new Scene(parent));
        stage.getIcons().add(new Image("icons/welcome.jpg"));
        stage.show();
        exportedStage = stage;
    }

    @Override
    public void stop() {
        applicationContext.close();
    }
}
