package se.kth.iv1350.model;

/**
 * A listener interface that receives notifications when the total revenue changes.
 */
public interface RegisterObserver {

    /**
     * Invoked when a sale is paid.
     * @param totalRevenue The total revenue of POS.
     */
    void totalRevenueChanged(Amount totalRevenue);
}
