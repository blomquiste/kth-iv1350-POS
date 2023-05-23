package se.kth.iv1350.model;
import java.time.LocalDateTime;
import java.util.*;

import se.kth.iv1350.integration.*;

import static java.util.stream.Collectors.toList;

/**
 * Represents a particular sale.
 */
public class Sale {
    private LocalDateTime timeOfSale;
    private Map<Integer, Item> shoppingCart = new HashMap<>();
    private List<RegisterObserver> registerObservers = new ArrayList<>();
    private CashPayment payment;
    private DiscountDTO discount = new DiscountDTO();
    private ItemRegistry itemRegistry;
    private Amount totalAmount;
    private Amount totalVAT;
    private Amount discountAmount;

    /**
     * Creates a new instance, representing a sale made by a customer.
     * @param itemRegistry The data stores information about all items (item catalog linked to external inventory system).
     */
    public Sale(ItemRegistry itemRegistry){
        this.timeOfSale = LocalDateTime.now();
        this.itemRegistry = itemRegistry;
    }

    /**
     * Adds an item to the shopping cart.
     * @param itemID The item identifier.
     * @param quantity The item quantity.
     * @throws ItemNotFoundException when item ID does not exist in inventory
     * @throws ItemRegistryException when there is an unknown fail with inventory system
     */
    public void addItem(int itemID, int quantity) throws ItemNotFoundException {
        if (shoppingCart.containsKey(itemID)) {
            this.shoppingCart.get(itemID).addToQuantity(quantity);
        }
        else {
            ItemDTO itemInfo = itemRegistry.getItemInfo(itemID);
            Item item = new Item(itemInfo, quantity);
            shoppingCart.put(itemID, item);
        }
    }

    /**
     * Adds an item to the shopping cart.
     * @param itemID The item identifier.
     */
    public void addItem(int itemID) throws ItemNotFoundException {
        addItem(itemID, 1);
    }

    /**
     * Gets the total amount for the current sale.
     * @return The total amount of the current sale.
     */
    public Amount getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gets the total VAT amount for the current sale.
     * @return The total VAT amount of the current sale.
     */
    public Amount getTotalVAT() {
        return totalVAT;
    }

    /**
     * Gets the discount amount for the current sale.
     * @return The discount amount of the current sale.
     */
    public Amount getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Calculates the total cost of the shopping cart, including possible discount.
     * @return The running total as a {@link Amount}.
     */
    Amount calculateRunningTotal() {
        Amount runningTotal = new Amount(0);
        List<Amount> totalPrices = getCollectionOfItems()
                .stream()
                .map(Item::getTotalPrice)
                .collect(toList());
        runningTotal = runningTotal.plus(totalPrices);

        return runningTotal;
    }

    /**
     * Calculates the total VAT of items in the shopping cart.
     * @return The total VAT amount as a {@link Amount}.
     */
    Amount calculateTotalVATAmount() {
        Amount totalVATAmount = new Amount(0);
        List<Amount> vatAmounts = getCollectionOfItems().stream().map(Item::getVatAmount).collect(toList());
        totalVATAmount = totalVATAmount.plus(vatAmounts);
//        totalVATAmount = totalVATAmount.multiply(discount.getDiscountMultiplier());

        return totalVATAmount;
    }

    /**
     * Gets payment for the current sale.
     * @return The payment of the current sale.
     */
    CashPayment getPayment(){
        return payment;
    }

    /**
     * Gets a collection of the items in the shopping cart.
     * @return A {@link Collection} of the items in the shopping cart.
     */
    Collection<Item> getCollectionOfItems() {
        return shoppingCart.values();
    }

    /**
     * Applies discount to the shopping cart.
     * @param discount The discount information as a {@link DiscountDTO}.
     */
    public void applyDiscount(DiscountDTO discount){
        this.discount = discount;
    }

    /**
     * The sale is paid by the specified payment
     * @param payment The payment used to pay for
     * this sale, as a {@link CashPayment}.
     */
    public void pay(CashPayment payment) {
        payment.calculateTotalCost(this);
        this.payment = payment;
    }

    /**
     * Prints a receipt for the current sale on specified printer.
     * @param printer The printer where the receipt is printed.
     */
    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(this);
        printer.printReceipt(receipt);
        notifyObservers();
    }

    /**
     * Displays the currently registered items.
     * @param display The display where the output is displayed.
     * @return Sale information as a {@link SaleDTO}.
     */
    public SaleDTO displayOpenSale(Display display) {
        SaleOutput saleOutput = new SaleOutput(this);
        display.displayOpenSale(saleOutput);
        return saleOutput.getSaleInfo();
    }

    /**
     * Displays the checkout shopping cart with total amount and vat,
     * including all registered items.
     * @param display The display where the output is displayed.
     * @return
     */
    public SaleDTO displayCheckout(Display display) {
        SaleOutput saleOutput = new SaleOutput(this);
        display.displayCheckout(saleOutput);
        return saleOutput.getSaleInfo();
    }

    /**
     * Updates the external inventory system with a {@link Collection}
     * of the sold items.
     */
    public void updateInventory() {
        itemRegistry.updateInventory(getCollectionOfItems());
    }

    /**
     * Ends the sale by calculating VAT and discount and updates the total amount.
     */
    public void endSale() {
        Amount runningTotal = calculateRunningTotal();
        Amount runningVAT = calculateTotalVATAmount();
        if (discount.getDiscountRate() > 0) {
            this.discountAmount = runningTotal.multiply(discount.getDiscountRate());
            this.totalAmount = runningTotal.multiply(this.discount.getDiscountMultiplier());
            this.totalVAT = runningVAT.multiply(this.discount.getDiscountMultiplier());
        } else {
            this.totalAmount = new Amount(runningTotal);
            this.totalVAT = new Amount(runningVAT);
        }
    }

    private void notifyObservers() {
        for (RegisterObserver obs : registerObservers) {
            obs.totalRevenueChanged(this.totalAmount);
        }
    }

    /**
     * Adds observer to the list of observers
     * @param observer the observer being added
     */
    public void addObserver(RegisterObserver observer) {
        registerObservers.add(observer);
    }
}