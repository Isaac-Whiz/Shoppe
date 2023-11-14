package com.shoppeapp.shoppe.purchase;

import com.shoppeapp.shoppe.product.Product;
import com.shoppeapp.shoppe.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Purchase")
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    @SequenceGenerator(name = "purchase_id_sequence", sequenceName = "purchase_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_id_sequence")
    private long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_purchase_id", foreignKey = @ForeignKey(name = "user_purchase_id_fk"))
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "purchases")
    private List<Product> products = new ArrayList<>();

    @Column(name = "product_quantity", nullable = false, columnDefinition = "NUMERIC")
    private int productQuantity;

    @Column(name = "purchase_price", nullable = false, columnDefinition = "NUMERIC")
    private double purchasePrice;

    @Column(name = "purchase_time", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime purchase_time;

    public Purchase(int productQuantity, double purchasePrice, LocalDateTime purchase_time) {
        this.productQuantity = productQuantity;
        this.purchasePrice = purchasePrice;
        this.purchase_time = purchase_time;
    }
}
