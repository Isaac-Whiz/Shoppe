package com.shoppeapp.shoppe.product;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class Product {

    @Column(name = "product_id", updatable = false)
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private long id;


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
