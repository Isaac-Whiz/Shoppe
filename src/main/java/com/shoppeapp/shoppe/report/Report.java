package com.shoppeapp.shoppe.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Report")
@Table(name = "report")
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @Column(name = "report_id")
    @SequenceGenerator(name = "report_id_sequence", sequenceName = "report_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_sequence")
    private long reportId;

    @Getter
    @Setter
    @Column(name = "most_selling_user", nullable = false, columnDefinition = "TEXT")
    private String mostSellingUser;

    @Getter
    @Setter
    @Column(name = "least_selling_user", nullable = false, columnDefinition = "TEXT")
    private String leastSellingUser;

    @Getter
    @Setter
    @Column(name = "least_selling_category", nullable = false, columnDefinition = "TEXT")
    private String leastSellingCategory;

    @Getter
    @Setter
    @Column(name = "most_selling_category", nullable = false, columnDefinition = "TEXT")
    private String mostSellingCategory;


    @Getter
    @Setter
    @Column(name = "most_selling_item", nullable = false, columnDefinition = "TEXT")
    private String mostSellingItem;

    @Getter
    @Setter
    @Column(name = "least_selling_item", nullable = false, columnDefinition = "TEXT")
    private String leastSellingItem;

    @Getter
    @Setter
    @Column(name = "total_purchases", nullable = false, columnDefinition = "NUMERIC")
    private long totalPurchases;

    @Getter
    @Setter
    @Column(name = "projected_total_profits", nullable = false, columnDefinition = "NUMERIC")
    private int projectedTotalProfits;

    @Getter
    @Setter
    @Column(name = "actual_total_profits", nullable = false, columnDefinition = "NUMERIC")
    private int actualTotalProfits;

    @Getter
    @Setter
    @Column(name = "available_stock", nullable = false, columnDefinition = "NUMERIC")
    private int availableStock;


    public Report(String mostSellingUser, String leastSellingUser, String leastSellingCategory, String mostSellingCategory, String mostSellingItem, String leastSellingItem, long totalPurchases, int projectedTotalProfits, int actualTotalProfits, int availableStock) {
        this.mostSellingUser = mostSellingUser;
        this.leastSellingUser = leastSellingUser;
        this.leastSellingCategory = leastSellingCategory;
        this.mostSellingCategory = mostSellingCategory;
        this.mostSellingItem = mostSellingItem;
        this.leastSellingItem = leastSellingItem;
        this.totalPurchases = totalPurchases;
        this.projectedTotalProfits = projectedTotalProfits;
        this.actualTotalProfits = actualTotalProfits;
        this.availableStock = availableStock;
    }
}
