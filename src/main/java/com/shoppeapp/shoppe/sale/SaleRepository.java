package com.shoppeapp.shoppe.sale;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Nonnull
    @Override
    List<Sale> findAll();
}
