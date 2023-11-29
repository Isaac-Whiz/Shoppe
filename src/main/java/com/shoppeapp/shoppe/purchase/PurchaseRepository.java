package com.shoppeapp.shoppe.purchase;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Nonnull
    @Override
    List<Purchase> findAll();

    List<Purchase> findPurchaseByProductProductName(String name);

}
