package com.shoppeapp.shoppe.sale;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.util.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Sale")
@Table(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @Column(name = "sale_id", updatable = false)
    @SequenceGenerator(name = "sale_id_sequence", sequenceName = "sale_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_id_sequence")
    private long id;

    @Embedded
    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    @Column(name = "sold_by", nullable = false, columnDefinition = "TEXT")
    private String soldBy = Util.setUsername().getName();

    @Getter
    @Setter
    @Column(name = "quantity_sold", nullable = false, columnDefinition = "BIGINT")
    private long quantitySold;

    @Getter
    @Setter
    @Column(name = "price_sold", nullable = false, columnDefinition = "NUMERIC")
    private double priceSold;

    @Getter
    @Setter
    @Column(name = "time_sold", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime timeSold;

    public Sale(Product product, long quantitySold, double priceSold, LocalDateTime timeSold) {
        this.product = product;
        this.quantitySold = quantitySold;
        this.priceSold = priceSold;
        this.timeSold = timeSold;
    }

    public Sale(Product product, String soldBy, long quantitySold, double priceSold, LocalDateTime timeSold) {
        this.product = product;
        this.soldBy = soldBy;
        this.quantitySold = quantitySold;
        this.priceSold = priceSold;
        this.timeSold = timeSold;
    }
}
