package com.shoppeapp.shoppe.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GenerateReport {

    private static final ReportsController reportsController = new ReportsController();

    public static void generateReport() throws FileNotFoundException, JRException {

        String filePath = "C:\\Users\\KALTECH\\Documents\\Shoppe\\src\\main" +
                "\\resources\\report_templates\\report.jrxml";
        JasperReport report = JasperCompileManager.compileReport(filePath);

        Map<String, Object> parameters = getParameters();

        JRBeanCollectionDataSource dataSource = new
                JRBeanCollectionDataSource(ReportsController.getSortedPurchasesList());


        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(
                jasperPrint,
                getDownloadsDirectoryPath()
                        + "\\ShoppeReport" + LocalDate.now() + ".pdf");
    }

    private static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();

        if (reportsController.findMostSellingCategory() != null) {

            parameters.put("lblMostCat", reportsController.findMostSellingCategory());
            parameters.put("lblLeastCat", reportsController.findLeastSellingCategory());
            parameters.put("lblMostItem", reportsController.findMostSellingItem());
            parameters.put("lblLeastItem", reportsController.findLeastSellingItem());
            parameters.put("lblMostUser", reportsController.lblMostUser);
            parameters.put("lblLeastUser", reportsController.lblLeastUser);
            parameters.put("lblTotalPurchases", String.valueOf(reportsController.findTotalPurchases()));
            parameters.put("lblProjectedProfits", String.valueOf(reportsController.findTotalProjectedProfits()));
            parameters.put("lblActualProfits", String.valueOf(reportsController.findTotalActualProfits()));
            parameters.put("lblCatNo", String.valueOf(reportsController.findCategoryNo()));
            parameters.put("lblItemNo", String.valueOf(reportsController.findItemTypes()));
            parameters.put("lblUserNo", String.valueOf(ReportService.findUserNo()));
            return parameters;
        }
        return new HashMap<>();
    }


    public static String getDownloadsDirectoryPath() {
        String downloadsKey = "user.home";
        String homeDirectory = System.getProperty(downloadsKey);
        return homeDirectory + "\\Downloads";
    }
}

