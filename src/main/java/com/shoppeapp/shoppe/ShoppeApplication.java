package com.shoppeapp.shoppe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class ShoppeApplication extends Application {

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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Windows/main.fxml"));
    loader.setControllerFactory(applicationContext::getBean);
    Parent parent = loader.load();

        stage.setTitle("Shoppe");
        stage.setResizable(false);
        stage.setScene(new Scene(parent, 400, 600));
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }
}
