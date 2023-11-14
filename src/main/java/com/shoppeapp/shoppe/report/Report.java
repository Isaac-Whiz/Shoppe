package com.shoppeapp.shoppe.report;

import com.shoppeapp.shoppe.user.User;
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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_report_id",
            foreignKey = @ForeignKey(name = "user_report_id_fk"))
    private User user;

    @Getter
    @Setter
    @Column(name = "daily_sales", nullable = false, columnDefinition = "NUMERIC")
    private long dailySales;

    @Getter
    @Setter
    @Column(name = "quantity_sold", nullable = false, columnDefinition = "NUMERIC")
    private int quantitySold;

    @Getter
    @Setter
    @Column(name = "most_selling", nullable = false, columnDefinition = "TEXT")
    private String mostSelling;

    @Getter
    @Setter
    @Column(name = "least_selling", nullable = false, columnDefinition = "TEXT")
    private String leastSelling;

    @Getter
    @Setter
    @Column(name = "available_stock", nullable = false, columnDefinition = "NUMERIC")
    private int availableStock;

    @Getter
    @Setter
    @Column(name = "projected_profit", nullable = false, columnDefinition = "NUMERIC")
    private double projectedProfits;

    @Getter
    @Setter
    @Column(name = "net_cash_position", nullable = false, columnDefinition = "NUMERIC")
    private double netCashPosition;


    public Report(long dailySales, int quantitySold,
                  String mostSelling, String leastSelling,
                  int availableStock, double projectedProfits,
                  double netCashPosition) {
        this.dailySales = dailySales;
        this.quantitySold = quantitySold;
        this.mostSelling = mostSelling;
        this.leastSelling = leastSelling;
        this.availableStock = availableStock;
        this.projectedProfits = projectedProfits;
        this.netCashPosition = netCashPosition;
    }
}
