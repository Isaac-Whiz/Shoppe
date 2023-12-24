package com.shoppeapp.shoppe;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.purchase.PurchaseService;
import com.shoppeapp.shoppe.sale.Sale;
import com.shoppeapp.shoppe.sale.SaleService;
import com.shoppeapp.shoppe.user.UserService;
import com.shoppeapp.shoppe.util.Util;
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
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
public class ShoppeApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private static boolean methodExecuted = false;

    public static void main(String[] args) {
        executeDatabaseScript();
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
    }

    public static void executeDatabaseScript() {

        if (!methodExecuted) {
            var logger = Util.getLogger(ShoppeApplication.class.getName());

            try {
                InputStream inputStream = ShoppeApplication.class.getResourceAsStream("/sql/create_db_script.sql");
                if (inputStream == null) {
                    throw new RuntimeException("SQL script not found in resources.");
                }

                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                String sqlScript = new String(buffer);

                String jdbcUrl = "jdbc:postgresql://localhost:5432/shoppe";
                String username = "whiz";
                String password = "whiz";
                try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                     Statement statement = connection.createStatement()) {

                    statement.execute(sqlScript);

                    methodExecuted = true;

                    logger.log(Level.FINE, "Database script executed successfully.");

                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error executing the database script: " + e.getMessage());
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error loading the database script: " + e.getMessage());
            }
        }
    }
    @Bean
    CommandLineRunner commandLineRunner(UserService service,
                                        SaleService saleService,
                                        PurchaseService purchaseService) {
        return args -> {
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Juma", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Juma", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Juma", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Juma", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"James", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"James", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"James", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Jacob", 23, 4000, LocalDateTime.now()));
//            SaleService.save(new Sale(new Product("Books", "Stationary"),"Jacob", 23, 4000, LocalDateTime.now()));
        };
    }
    @Override
    public void stop() {
        applicationContext.close();
    }
}
