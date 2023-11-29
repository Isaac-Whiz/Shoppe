package com.shoppeapp.shoppe.purchase;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.util.Util;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Purchase")
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    @SequenceGenerator(name = "purchase_id_sequence", sequenceName = "purchase_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_id_sequence")
    private long purchaseId;

    @Embedded
    @Getter
    private Product product;


    @Column(name = "purchased_by", nullable = false, columnDefinition = "TEXT")
    private String purchasedBy = Util.setUsername().getName();


    @Getter
    @Setter
    @Column(name = "product_quantity", nullable = false, columnDefinition = "NUMERIC")
    private int productQuantity;

    @Getter
    @Setter
    @Column(name = "purchase_price", nullable = false, columnDefinition = "NUMERIC")
    private double purchasePrice;

    @Getter
    @Setter
    @Column(name = "selling_price", nullable = false, columnDefinition = "NUMERIC")
    private double sellingPrice;

    @Getter
    @Setter
    @Column(name = "projected_sales", nullable = false, columnDefinition = "NUMERIC")
    private double projectedSales;

    @Getter
    @Setter
    @Column(name = "projected_profits", nullable = false, columnDefinition = "NUMERIC")
    private double projectedProfits;

    @Getter
    @Setter
    @Column(name = "purchase_time", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime purchaseTime;


    public Purchase(Product product,
                    String purchasedBy,
                    int productQuantity,
                    double purchasePrice,
                    double sellingPrice,
                    double projectedSales,
                    double projectedProfits,
                    LocalDateTime purchaseTime) {
        this.product = product;
        this.purchasedBy = purchasedBy;
        this.productQuantity = productQuantity;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.projectedSales = projectedSales;
        this.projectedProfits = projectedProfits;
        this.purchaseTime = purchaseTime;
    }


}
