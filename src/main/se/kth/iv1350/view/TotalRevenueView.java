package se.kth.iv1350.view;

import se.kth.iv1350.model.Amount;
import se.kth.iv1350.model.RegisterObserverOutput;

public class TotalRevenueView extends RegisterObserverOutput {

    /**
     * Shows the total revenue to the view.
     * @param totalRevenue the total revenue
     */
    @Override
    protected void doShowTotalRevenue(Amount totalRevenue){
        System.out.println("Total revenue: " + totalRevenue);
    }

    /**
     * Would handle exceptions if there were any that could be thrown.
     * @param exception the exception thrown
     */
    @Override
    protected void handleErrors(Exception exception) {

    }

}
