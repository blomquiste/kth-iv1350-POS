package se.kth.iv1350.model;

/**
 * Represents one specific payment for one sale.
 * The sale is paid in cash.
 */
public class CashPayment {
    private final Amount paidAmt;
    private Amount totalCost;

    /**
     * Creates a new instance of the cash that is
     * paid for the entire sale.
     * @param paidAmt The amount of cash that was given by the customer
     */
    public CashPayment(Amount paidAmt){
        this.paidAmt = paidAmt;
    }

    /**
     * Calculates the total cost of the specified {@link Sale}
     * @param paidSale The sale that the customer is paying.
     */
    void calculateTotalCost(Sale paidSale) {
        this.totalCost = paidSale.getTotalAmount();
    }

    /**
     * @return The amount of cash that was given by the customer.
     */
    Amount getPaidAmt() {
        return paidAmt;
    }

    /**
     * @return The total cost of the rental that was paid.
     */
    Amount getTotalCost() {
        return totalCost;
    }

    /**
     * @return The amount of change the customer shall receive.
     */
    Amount getChange() {
        return paidAmt.minus(totalCost);
    }
}