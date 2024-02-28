package com.shoppeapp.shoppe.purchase;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.user.UserController;
import com.shoppeapp.shoppe.util.Util;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.net.URI;
import java.time.LocalDateTime;

@Controller
public class PurchaseController extends Util {

    public MenuItem sales;
    public MenuItem exit;
    public TextField txtName;
    public TextField txtPurchasePrice;
    public TextField txtQuantity;
    public TextField txtCategory;
    public TextField txtSellingPrice;
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colQuantity;
    public TableColumn colPurchasePrice;
    public TableColumn colSellingPrice;
    public TableColumn colProjectedSales;
    public TableColumn colProjectedProfits;
    public TableColumn colPurchasedBy;
    public TableColumn colTimePurchased;
    public Button btnAddPurchase;
    public TableView purchaseTable;
    public MenuItem logout;
    public MenuItem report;
    public MenuItem add_items;
    public MenuItem delete;
    public MenuItem about;

    @FXML
    public void initialize() {
        addPurchase();
        inflatePurchaseTable();
        selectInput();
        censorInput();
        handleMenuEvents();
        logout();
    }


    private void addPurchase() {
        btnAddPurchase.setOnAction(actionEvent -> savePurchase());
    }
    private void handleMenuEvents() {
         sales.setOnAction(actionEvent -> navigateToSales());
        report.setOnAction(actionEvent -> navigateToReport());
        exit.setOnAction(actionEvent -> exit());
        disableMenuItem(add_items);
        disableMenuItem(delete);
        about.setOnAction(actionEvent -> openGitRepo());
    }

    private void savePurchase() {
        if (areFiledInputs()) {
            var name = txtName.getText().trim();
            var category = txtCategory.getText().trim();
            var purchasePrice = Double.parseDouble(txtPurchasePrice.getText().trim());
            var sellingPrice = Double.parseDouble(txtSellingPrice.getText().trim());
            var quantity = Integer.parseInt(txtQuantity.getText().trim());

            PurchaseService.save(new Purchase(new Product(name, category),
                    getUSERNAME(),
                    quantity,
                    purchasePrice,
                    sellingPrice,
                    calculateProjectedSales(sellingPrice, quantity),
                    calculateProjectedProfits(sellingPrice, purchasePrice, quantity),
                    LocalDateTime.now()));
            inflatePurchaseTable();

        } else {
            showAlert("Information", "Fill all the fields please.", Alert.AlertType.INFORMATION);
        }
    }

    private void inflatePurchaseTable() {
        var observableList = FXCollections.observableArrayList();
        observableList.addAll(PurchaseService.getPurchases());

        colName.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Purchase, String>, ObservableValue>)
                cellDataFeatures -> new SimpleStringProperty(
                        cellDataFeatures.getValue().getProduct().getProductName()));
        colCategory.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Purchase, String>, ObservableValue>)
                cellDataFeatures -> new SimpleStringProperty(
                        cellDataFeatures.getValue().getProduct().getProductCategory()));
        colPurchasedBy.setCellValueFactory(new PropertyValueFactory<>("purchasedBy"));
        colPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colProjectedProfits.setCellValueFactory(new PropertyValueFactory<>("projectedProfits"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        colProjectedSales.setCellValueFactory(new PropertyValueFactory<>("projectedSales"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colTimePurchased.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));

        purchaseTable.setItems(observableList);
        scrollToLastItem(observableList, purchaseTable);

    }

    private double calculateProjectedSales(double sellingPrice, int quantity) {
        return (sellingPrice * quantity);
    }

    private double calculateProjectedProfits(double sellingPrice, double purchasePrice, int quantity) {
        return ((sellingPrice * quantity) - (calculateProjectedSales(purchasePrice, quantity)));
    }

    private void selectInput() {
        selectInput(txtName);
        selectInput(txtCategory);
        selectInput(txtPurchasePrice);
        selectInput(txtSellingPrice);
        selectInput(txtQuantity);
    }

    private void navigateToSales() {
        switchScenes("/windows/sales.fxml", "Sales", btnAddPurchase);
    }

    private void navigateToReport() {
        switchScenes("/windows/reports.fxml", "Reports", btnAddPurchase);
    }
    private boolean areFiledInputs() {
        return !txtName.getText().isEmpty() &&
                !txtCategory.getText().isEmpty() &&
                !txtPurchasePrice.getText().isEmpty() &&
                !txtSellingPrice.getText().isEmpty() &&
                !txtQuantity.getText().isEmpty();
    }

    private void censorInput() {
        allowNumericInput(txtQuantity);
        allowNumericInput(txtSellingPrice);
        allowNumericInput(txtPurchasePrice);
        allowTextInput(txtName);
        allowTextInput(txtCategory);
    }

    private void logout() {
        logout.setOnAction(actionEvent -> switchScenes("/windows/user.fxml", "Shoppe", btnAddPurchase));
    }

    private void exit() {
        confirmExit();
    }
}

