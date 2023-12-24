package com.shoppeapp.shoppe.sale;

import com.shoppeapp.shoppe.purchase.Purchase;
import com.shoppeapp.shoppe.purchase.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    private static SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        SaleService.saleRepository = saleRepository;
    }
    public static List<Purchase> getProductNames() {
        return PurchaseService.getPurchases();
    }

    public static List<Purchase> findPurchaseByName(String name) {
        return PurchaseService.findPurchaseByName(name);
    }

    public static List<Sale> getSales() {
        return saleRepository.findAll();
    }
    public  static void save(Sale sale) {
        saleRepository.save(sale);
    }
    public long count(){
        return saleRepository.count();
    }
}
