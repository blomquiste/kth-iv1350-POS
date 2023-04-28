package src.se.kth.iv1350.integration;

import src.se.kth.iv1350.model.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all sales performed in the POS.
 */
public class SaleLog {
    private List<Sale> sales = new ArrayList<>();
    public SaleLog(){
        //TODO construct
    }

    /**
     * Saves the specified sale permanently
     * @param sale The sale that will be saved.
     */
    public void logSale(Sale sale) {
        sales.add(sale);
    }
}
