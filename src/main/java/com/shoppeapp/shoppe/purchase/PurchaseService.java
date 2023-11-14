package com.shoppeapp.shoppe.purchase;

public class PurchaseService {

    private final PurchaseRepository purchaseRepository;


    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
}
