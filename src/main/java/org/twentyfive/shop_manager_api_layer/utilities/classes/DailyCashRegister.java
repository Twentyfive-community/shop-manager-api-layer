package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = "€ "+String.format("%.2f", totalRevenue);
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = "€ "+String.format("%.2f", totalCost);
    }

    public void setTotal(double totalRevenue, double totalCost) {
        double totalNumber = totalRevenue - totalCost;
        this.total = "€ "+String.format("%.2f", totalNumber);
    }
}
