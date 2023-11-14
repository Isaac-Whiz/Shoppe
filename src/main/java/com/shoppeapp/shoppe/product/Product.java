package com.shoppeapp.shoppe.product;

import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Column(name = "product_id", updatable = false)
    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private long id;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_sale",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private List<Purchase> purchases = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_sale",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Sale> sales = new ArrayList<>();

    @Getter
    @Setter
    @Column(name = "product_name", nullable = false, columnDefinition = "TEXT")
    private String productName;


    @Getter
    @Setter
    @Column(name = "product_category", nullable = false, columnDefinition = "TEXT")
    private String productCategory;

    public Product(String productName, String productCategory) {
        this.productName = productName;
        this.productCategory = productCategory;
    }
}
