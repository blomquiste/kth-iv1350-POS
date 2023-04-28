package src.se.kth.iv1350.dto;

import src.se.kth.iv1350.model.Amount;
import src.se.kth.iv1350.model.Item;

import java.util.Arrays;

@Deprecated
public class CurrentSaleDTO {
    private Item[] items; // TODO ändra namn till shoppingCart eller enbart cart?
    private Amount runningTotal;

    public CurrentSaleDTO(Item[] items, Amount runningTotal) {
        this.items = items;
        this.runningTotal = new Amount(runningTotal);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Item item: this.items) {
            builder.append("Item: " + item.getItemDTO().getName() + ", "); //TODO är det namn eller description
            builder.append(item.getTotalAmount());
            builder.append("\n");
        }
        builder.append("\n");
        builder.append("Running total: " + this.runningTotal);
        return builder.toString();
    }
}