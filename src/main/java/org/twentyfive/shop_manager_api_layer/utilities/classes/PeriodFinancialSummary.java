package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.twentyfive.shop_manager_api_layer.utilities.statics.ParseUtility.parseDouble;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodFinancialSummary {

    private String period;

    private String totalRevenueCashRegisters;
    private String totalCostCashRegisters;

    private String totalCostSuppliers;

    private String total;

    //TODO aggiungere i clienti.

    public void setTotalCostSuppliers(double totalCostSuppliers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        this.totalCostSuppliers = df.format(totalCostSuppliers);
    }

    public void setPeriod(LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String s1 = start.format(formatter);
        String s2 = end.format(formatter);

        this.period = s1 + " - " + s2;
    }

    public String getTotal() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        double revenue = parseDouble(totalRevenueCashRegisters);
        double costRegisters = parseDouble(totalCostCashRegisters);
        double costSuppliers = parseDouble(totalCostSuppliers);

        double total = revenue - (costRegisters + costSuppliers);

        return df.format(total);
    }


}
