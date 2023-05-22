package se.kth.iv1350.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A template for a register observer that outputs the total revenue when it
 * has changed.
 */
public abstract class RegisterObserverOutput
        implements RegisterObserver {

    private Amount sumRevenue = new Amount(0);

    /**
     * Called when total revenue has been changed.
     * @param totalRevenue the new total revenue
     */
    @Override
    public void totalRevenueChanged(Amount totalRevenue) {
        showTotalRevenue(totalRevenue);
    }

    private void showTotalRevenue(Amount totalRevenue) {
        sumRevenue = sumRevenue.plus(totalRevenue);
        try {
            doShowTotalRevenue(sumRevenue);
        } catch (Exception exception) {
            handleErrors(exception);
        }
    }

    /**
     * Shows the total revenue.
     * @param totalRevenue the new total revenue
     * @throws Exception any exception thrown when showing the total revenue
     */
    protected abstract void doShowTotalRevenue(Amount totalRevenue)
            throws Exception;

    /**
     * Error handling for any exceptions thrown when showing the total revenue.
     * @param exception the exception thrown
     */
    protected abstract void handleErrors(Exception exception);

}