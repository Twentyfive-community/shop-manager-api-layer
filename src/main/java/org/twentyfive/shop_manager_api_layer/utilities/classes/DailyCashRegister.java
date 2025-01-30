package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCashRegister {
    private Long id;
    private String timeSlot;
    private String totalRevenue;
    private String totalCost;
    private String total;
    private boolean done;

    private List<EntryClosureDetails> entryClosureDetails;
    private List<ComposedEntryClosureDetails> composedEntryClosureDetails;

    public void setTotalRevenue(double totalRevenue) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        this.totalRevenue = df.format(totalRevenue);
    }

    public void setTotalCost(double totalCost) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        this.totalCost = df.format(totalCost);
    }

    public void setTotal(double totalRevenue, double totalCost) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        double totalNumber = totalRevenue - totalCost;
        this.total = df.format(totalNumber);
    }

}
