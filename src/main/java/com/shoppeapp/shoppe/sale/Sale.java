package com.shoppeapp.shoppe.sale;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_sale_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "user_sale_id"))
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "sales")
    private List<Product> products = new ArrayList<>();

    @Getter
    @Setter
    @Column(name = "product_sold", nullable = false, columnDefinition = "TEXT")
    private String productSold;

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

    public Sale(String productSold, long quantitySold, double priceSold, LocalDateTime timeSold) {
        this.productSold = productSold;
        this.quantitySold = quantitySold;
        this.priceSold = priceSold;
        this.timeSold = timeSold;
    }
}
