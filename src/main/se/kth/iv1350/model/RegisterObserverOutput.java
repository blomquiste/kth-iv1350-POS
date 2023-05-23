package se.kth.iv1350.model;

/**
 * A template for the register observer that calculates the
 * sum of revenue since the program started.
 */
public abstract class RegisterObserverOutput
        implements RegisterObserver {

    private Amount totalRevenue = new Amount(0);

    /**
     * Called when total revenue is to be updated.
     * @param newRevenue the new revenue to be added
     */
    @Override
    public void totalRevenueChanged(Amount newRevenue) {
        showTotalRevenue(newRevenue);
    }

    /**
     * Adds revenue to the total revenue and shows it.
     * @param newRevenue the newest revenue
     */
    private void showTotalRevenue(Amount newRevenue) {
        totalRevenue = totalRevenue.plus(newRevenue);
        try {
            doShowTotalRevenue(totalRevenue);
        } catch (Exception ex) {
            handleErrors(ex);
        }
    }

    /**
     * Shows the total revenue.
     * @param totalRevenue the updated total revenue
     * @throws Exception any exception thrown while showing revenue
     */
    protected abstract void doShowTotalRevenue(Amount totalRevenue)
            throws Exception;

    /**
     * Handles errors that are thrown.
     * @param ex the exception thrown
     */
    protected abstract void handleErrors(Exception ex);

}