package com.shoppeapp.shoppe;

import com.shoppeapp.shoppe.user.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/authenticate.fxml"));
    loader.setControllerFactory(applicationContext::getBean);
    Parent parent = loader.load();

        stage.setTitle("Shoppe");
        stage.setMaximized(true);
        stage.getIcons().add(new Image("icons/main-form-icon.png"));
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService service) {
        return args -> {
//            service.save(new User("John", "john", LocalDateTime.now()));
//            service.save(new User("Joram", "joram", LocalDateTime.now()));
//            service.save(new User("James", "james", LocalDateTime.now()));
            System.out.println(service.count());
        };
    }
    @Override
    public void stop() {
        applicationContext.close();
    }
}
