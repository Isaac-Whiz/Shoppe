package com.shoppeapp.shoppe.report;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.purchase.PurchaseService;
import com.shoppeapp.shoppe.sale.Sale;
import com.shoppeapp.shoppe.sale.SaleController;
import com.shoppeapp.shoppe.sale.SaleRepository;
import com.shoppeapp.shoppe.sale.SaleService;
import com.shoppeapp.shoppe.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ReportsController extends Util {
    public MenuItem sales;
    public MenuItem purchases;
    public MenuItem logout;
    public MenuItem exit;
    public Button btnGenerateReport;
    public Pane report_panel;
    public Label lblMostCate;
    public Label lblMostUser;
    public Label lblLeastUser;
    public Label lblTotalPurchases;
    public Label lblProjectedProfits;
    public Label lblActualProfits;
    public Label lblLeastItem;
    public Label lblMostItem;
    public Label lblLeastCate;
    public BarChart<String, Number> barChart;
    public PieChart pieChart;
    public ListView<String> listItemRanking;
    public Label lblCateNo;
    public Label lblUserNo;
    public Label lblItemTypes;


    @FXML
    private void initialize() {
        init();
        handleMenuEvents();
        inflateBarChart();
        inflatePieChart();
        inflateMiscellaneousAndOtherInfo();
        inflateListView();
        generateReport();
        disableReportBtn();
    }

    private void init() {

    }

    private void handleMenuEvents() {
        sales.setOnAction(actionEvent -> navigateToSales());
        purchases.setOnAction(actionEvent -> navigateToPurchases());
        logout.setOnAction(actionEvent -> logout());
        exit.setOnAction(actionEvent -> exit());
    }

    private void exit() {
        confirmExit();
    }

    private void navigateToSales() {
        switchScenes("/windows/sales.fxml", "Sales", btnGenerateReport);
    }

    private void inflateListView() {
        var sortedProducts = getSortedPurchasesList();
        listItemRanking.setItems(getObservableList(sortedProducts));
    }

    public static List<String> getSortedPurchasesList() {

        Map<String, Double> totalSalesByItem = new HashMap<>();

        SaleService.getSales().forEach(sale -> {
            var productName = sale.getProduct().getProductName();
            double salePrice = sale.getPriceSold();
            totalSalesByItem.merge(productName, salePrice, Double::sum);
        });

        return totalSalesByItem.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
    }


    private void inflateBarChart() {
        var category = "Category";
        var sales = "Sales";
        barChart.getXAxis().setSide(Side.BOTTOM);
        barChart.getXAxis().setLabel(category);
        barChart.getYAxis().setSide(Side.LEFT);
        barChart.getYAxis().setLabel(sales);

        XYChart.Series series = new XYChart.Series();

        // TODO: 12/6/2023 Change the colors of each serie
        SaleService.getSales().forEach(sale -> {
            series.getData().add(new XYChart.Data<>(
                    sale.getProduct().getProductCategory(),
                    sale.getQuantitySold()));
        });

        barChart.getData().add(series);
    }

    public Map<String, Integer> getPieChartMap() {
        var userOccurrenceMap = getMap();
        SaleService.getSales().forEach(sale -> {
            var soldBy = sale.getSoldBy();
            userOccurrenceMap.put(soldBy,
                    userOccurrenceMap.getOrDefault(soldBy, 0) + 1);
        });
        return userOccurrenceMap;
    }
    private void inflatePieChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        getPieChartMap().forEach((name, counter) -> pieChartData.add(new PieChart.Data(name, counter)));
        pieChart.getData().addAll(pieChartData);
    }

    private void inflateMiscellaneousAndOtherInfo() {
        lblMostCate.setText(findMostSellingCategory());
        lblLeastCate.setText(findLeastSellingCategory());
        lblMostItem.setText(findMostSellingItem());
        lblLeastItem.setText(findLeastSellingItem());
        lblMostUser.setText(findMostSellingUser());
        lblLeastUser.setText(findLeastSellingUser());
        lblTotalPurchases.setText(String.valueOf(findTotalPurchases()));
        lblProjectedProfits.setText(String.valueOf(findTotalProjectedProfits()));
        lblActualProfits.setText(String.valueOf(findTotalActualProfits()));
        lblUserNo.setText(String.valueOf(ReportService.findUserNo()));
        lblCateNo.setText(String.valueOf(findCategoryNo()));
        lblItemTypes.setText(String.valueOf(findItemTypes()));

    }

    public String findMostSellingCategory() {
        var occurrenceMap = getSortedSaleCategories();
        Optional<Map.Entry<String, Integer>> maxEntry =
                occurrenceMap.entrySet().stream().max(Map.Entry.comparingByValue());

        return maxEntry.map(Map.Entry::getKey).orElse(null);
    }

    public String findLeastSellingCategory() {
        var occurrenceMap = getSortedSaleCategories();
        Optional<Map.Entry<String, Integer>> minEntry =
                occurrenceMap.entrySet().stream().min(Map.Entry.comparingByValue());

        return minEntry.map(Map.Entry::getKey).orElse(null);
    }

    private void disableReportBtn() {
        if (SaleService.getSales().isEmpty()) {
            btnGenerateReport.setVisible(false);
        }
    }

    public Map<String, Integer> getSortedSaleCategories() {
        var stringIntegerMap = getMap();
        SaleService.getSales().forEach(sale -> stringIntegerMap.put(sale.getProduct().getProductCategory(), stringIntegerMap.getOrDefault(sale.getProduct().getProductCategory(), 0) + 1));
        return stringIntegerMap;
    }

    private Map<String, Integer> getMap() {
        return new HashMap<>();
    }

    private Map<String, Integer> getSortedSaleItems() {
        var stringIntegerMap = getMap();
        SaleService.getSales().forEach(sale -> stringIntegerMap.put(sale.getProduct().getProductName(), stringIntegerMap.getOrDefault(sale.getProduct().getProductName(), 0) + 1));
        return stringIntegerMap;
    }

    public String findMostSellingItem() {
        var occurrenceMap = getSortedSaleItems();
        Optional<Map.Entry<String, Integer>> maxEntry =
                occurrenceMap.entrySet().stream().max(Map.Entry.comparingByValue());

        return maxEntry.map(Map.Entry::getKey).orElse(null);
    }

    public String findLeastSellingItem() {
        var occurrenceMap = getSortedSaleItems();
        Optional<Map.Entry<String, Integer>> minEntry =
                occurrenceMap.entrySet().stream().min(Map.Entry.comparingByValue());

        return minEntry.map(Map.Entry::getKey).orElse(null);
    }

    public String findMostSellingUser() {
        var occurrenceMap = getSortedSaleUser();

        Optional<Map.Entry<String, Integer>> maxEntry =
                occurrenceMap.entrySet().stream().max(Map.Entry.comparingByValue());

        return maxEntry.map(Map.Entry::getKey).orElse(null);
    }

    public String findLeastSellingUser() {
        var occurrenceMap = getSortedSaleUser();

        Optional<Map.Entry<String, Integer>> minEntry =
                occurrenceMap.entrySet().stream().min(Map.Entry.comparingByValue());

        return minEntry.map(Map.Entry::getKey).orElse(null);
    }

    private Map<String, Integer> getSortedSaleUser() {
        var stringIntegerMap = getMap();
        SaleService.getSales().forEach(sale -> stringIntegerMap.put(sale.getSoldBy(), stringIntegerMap.getOrDefault(sale.getSoldBy(), 0) + 1));

        return stringIntegerMap;
    }

    public double findTotalPurchases() {
        return getPurchaseStream()
                .map(Purchase::getPurchasePrice)
                .collect(Collectors.summarizingDouble(Double::doubleValue)).getSum();
    }

    public double findTotalProjectedProfits() {
        return getPurchaseStream()
                .map(Purchase::getProjectedProfits)
                .collect(Collectors.summarizingDouble(Double::doubleValue)).getSum();
    }

    public double findTotalActualProfits() {
        var totalSales = findTotalSales();
        var totalPurchases = (int) findTotalPurchases();

        return Math.subtractExact(totalSales, totalPurchases);
    }

    public int findTotalSales() {
        return (int) SaleService.getSales().stream()
                .map(Sale::getPriceSold)
                .collect(Collectors.summarizingDouble(Double::doubleValue)).getSum();
    }

    public long findCategoryNo() {
        return new HashSet<>(getProductStream()
                .map(Product::getProductCategory).toList()).size();
    }

    public long findItemTypes() {
        return new HashSet<>(getProductStream()
                .map(Product::getProductName).toList()).size();
    }

    private Stream<Product> getProductStream() {
        return getPurchaseStream()
                .map(Purchase::getProduct);
    }

    private Stream<Purchase> getPurchaseStream() {
        return PurchaseService.getPurchases().stream();
    }

    private void navigateToPurchases() {
        switchScenes("/windows/purchases.fxml", "Purchases", btnGenerateReport);
    }

    private void generateReport() {
        btnGenerateReport.setOnAction(event -> {
            try {
                GenerateReport.generateReport();
                showAlert("Bingo", "Report generated to the downloads directory", Alert.AlertType.INFORMATION);
            } catch (FileNotFoundException | JRException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void logout() {
        switchScenes("/windows/user.fxml", "User", btnGenerateReport);
    }
}

