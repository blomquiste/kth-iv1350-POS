package se.kth.iv1350.view;

import se.kth.iv1350.model.Amount;
import se.kth.iv1350.model.RegisterObserverOutput;

/**
 * This class prints the total revenue to the view,
 * in this case that is by writing to System.out
 */
public class TotalRevenueView extends RegisterObserverOutput {

    /**
     * Creates a message and prints the total revenue to System.out
     * @param totalRevenue the total revenue
     */
    @Override
    protected void doShowTotalRevenue(Amount totalRevenue){
        System.out.println("Total revenue: " + totalRevenue);
    }

    /**
     * Handles exceptions.
     * @param ex the exception thrown
     */
    @Override
    protected void handleErrors(Exception ex) {
    }

}
