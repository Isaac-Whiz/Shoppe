package com.shoppeapp.shoppe.sale;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.purchase.PurchaseService;
import com.shoppeapp.shoppe.util.Util;
import jakarta.transaction.Transactional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.jfree.util.Log;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SaleController extends Util{

    public ChoiceBox choiceChooseItem;
    public TextField txtSellQuantity;
    public Button btnSell;
    public TextField txtSellCost;
    public Label txtCurrentUser;
    public MenuItem exit;
    public Pane sales_panel;
    public TableColumn colItem;
    public TableColumn colQuantity;
    public TableColumn colCategory;
    public TableColumn colCost;
    public TableView salesTable;
    public TextField txtSellPrice;
    public MenuItem purchases;
    public TableColumn colSaleItem;
    public TableColumn colSellingPrice;
    public TableView pricesTable;
    private final StringBuilder itemBuilder = new StringBuilder();
    public MenuItem report;
    public MenuItem logout;
    public Label lblResult;
    public Button btnCl;
    public Button btnSub;
    public Button btnAdd;
    public Button btnDiv;
    public Button btnEqual;
    public Button btnZero;
    public Button btnSeven;
    public Button btnEight;
    public Button btnNine;
    public Button btnFour;
    public Button btnTwo;
    public Button btnSix;
    public Button btnThree;
    public Button btnOne;
    public Button btnFive;
    public Button btnMulti;
    private final StringBuilder currentInput = new StringBuilder();
    public MenuItem add_items;
    public MenuItem delete;
    public MenuItem about;
    private double currentResult = 0;
    private String lastOperator = "";
    private Logger logger;



    @FXML
    public void initialize() {
        init();
        populateChooseItems();
        inflatePricesTable();
        getSelectedItem();
        inflateSalesTable();
        makeSale();
        selectInput();
        handMenuEvents();
        censorInput();
        logout();
    }

    private void populateChooseItems() {
        var set = new HashSet<>();
        var observableList = FXCollections.observableArrayList();
        if (!SaleService.getProductNames().isEmpty()) {
            SaleService.getProductNames().stream().map(Purchase::getProduct)
                    .map(Product::getProductName)
                    .forEach(set::add);
            observableList.addAll(set);
            choiceChooseItem.setItems(observableList);
        } else {
            logger.log(Level.INFO, "Can not populate choose items: New datasource instances");
        }
    }

    private void inflateSalesTable() {
        var observableList = getObservableList(SaleService.getSales());

        colItem.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Sale, String>, ObservableValue>)
                cellDataFeatures -> new SimpleStringProperty(
                        cellDataFeatures.getValue().getProduct().getProductName()));

        colCategory.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Sale, String>, ObservableValue>)
                cellDataFeatures -> new SimpleStringProperty(
                        cellDataFeatures.getValue().getProduct().getProductCategory()));

        colCost.setCellValueFactory(new PropertyValueFactory<>("priceSold"));

        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));

        salesTable.setItems(observableList);
        scrollToLastItem(observableList, salesTable);
    }

    private void inflatePricesTable() {
        var observableList = getObservableList(PurchaseService.getPurchases());

        colSaleItem.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Purchase, String>, ObservableValue>)
                cellDataFeatures -> new SimpleStringProperty(
                        cellDataFeatures.getValue().getProduct().getProductName()));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        pricesTable.setItems(observableList);
    }

    private void init() {
        txtCurrentUser.setText(Util.getUSERNAME());
        logger = getLogger(SaleController.class.getName());
    }
    private void handMenuEvents() {
        exit.setOnAction(actionEvent -> exit());
        purchases.setOnAction(actionEvent -> navigateToPurchases());
        report.setOnAction(actionEvent -> navigateToReport());
        disableMenuItem(delete);
        disableMenuItem(add_items);
        about.setOnAction(actionEvent -> openGitRepo());
    }
    private void getSelectedItem() {
        choiceChooseItem.setOnAction(actionEvent -> {
            itemBuilder.delete(0, itemBuilder.capacity());
            itemBuilder.append(choiceChooseItem.getValue().toString());
        });
    }

    @Transactional
    public void makeSale() {
        btnSell.setOnAction(actionEvent -> {

            if (isValidInput()) {
                showAlert("Information",
                        "Please enter quantity and price",
                        Alert.AlertType.INFORMATION);
            } else {
                StringBuilder quantity = new StringBuilder();
                StringBuilder price = new StringBuilder();
                quantity.append(txtSellQuantity.getText());
                price.append(txtSellPrice.getText());
                if (isNotItemSelected()) {
                    showAlert("Information", "Please choose an item", Alert.AlertType.INFORMATION);
                } else {
                    var selectedItem = itemBuilder.toString();
                    var productCategory = SaleService.findPurchaseByName(itemBuilder.toString())
                            .get(0).getProduct().getProductCategory();
                    try {
                        var convQuantity = Long.parseLong(String.valueOf(quantity));
                        var convPrice = Long.parseLong(String.valueOf(price));

                        var cost = Math.multiplyExact(
                                convQuantity,
                                convPrice);
                        txtSellCost.setText(String.valueOf(cost));
                        SaleService.save(new Sale(new Product(selectedItem, productCategory),
                                convQuantity,
                                cost,
                                LocalDateTime.now()));
                        inflateSalesTable();

                    } catch (NumberFormatException e) {
                        showAlert("Information",
                                "Please enter valid numbers",
                                Alert.AlertType.INFORMATION);
                    }
                }
            }
        });
    }

    private boolean isValidInput() {
        return txtSellQuantity.getText() == null
                || txtSellPrice.getText() == null
                || txtSellQuantity.getText().trim().isEmpty()
                || txtSellPrice.getText().trim().isEmpty();
    }

    private boolean isNotItemSelected() {
        return itemBuilder.isEmpty();
    }
    private void censorInput() {
        allowNumericInput(txtSellPrice);
        allowNumericInput(txtSellQuantity);
    }

    private void selectInput() {
        selectInput(txtSellPrice);
        selectInput(txtSellQuantity);
    }

    private void navigateToPurchases() {
        switchScenes("/windows/purchases.fxml", "Purchases", btnSell);
    }

    private void navigateToReport() {
        switchScenes("/windows/reports.fxml", "Reports", btnSell);
    }


    @FXML
    private void onNumberClicked(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        currentInput.append(value);
        lblResult.setText(String.valueOf(currentInput));
    }

    @FXML
    private void onSymbolClicked(ActionEvent event) {
        String operator = ((Button) event.getSource()).getText();
        performOperation();
        lastOperator = operator;
        currentInput.setLength(0);
    }

    @FXML
    private void onEqualsClicked() {
        performOperation();
        currentInput.setLength(0);
        lastOperator = "";
    }

    @FXML
    private void onClearClicked() {
        currentInput.setLength(0);
        currentResult = 0;
        lastOperator = "";
        updateResultLabel();
    }

    private void performOperation() {
        if (!currentInput.isEmpty()) {
            double inputValue = Double.parseDouble(currentInput.toString());
            switch (lastOperator) {
                case "":
                    currentResult = inputValue;
                    break;
                case "+":
                    currentResult += inputValue;
                    break;
                case "-":
                    currentResult -= inputValue;
                    break;
                case "X":
                    currentResult *= inputValue;
                    break;
                case "/":
                    if (inputValue != 0) {
                        currentResult /= inputValue;
                    } else {
                        lblResult.setText("Math Error");
                        return;
                    }
                    break;
            }
            updateResultLabel();
        }
    }

    private void updateResultLabel() {
        lblResult.setText(currentInput.isEmpty() ? currentInput.toString() : String.valueOf(currentResult));
    }
    private void logout() {
        logout.setOnAction(actionEvent -> {
            switchScenes("/windows/user.fxml", "Shoppe", btnSell);
        });
    }

    private void exit() {
        confirmExit();
    }
}
