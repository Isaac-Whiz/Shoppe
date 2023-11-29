package com.shoppeapp.shoppe.purchase;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private static PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        PurchaseService.purchaseRepository = purchaseRepository;
    }

    public static List<Purchase> findPurchaseByName(String name) {
        return PurchaseService.purchaseRepository.findPurchaseByProductProductName(name);
    }

    public static void save(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public static long count() {
        return purchaseRepository.count();
    }

    public  static List<Purchase> getPurchases() {
        return purchaseRepository.findAll();
    }
}
