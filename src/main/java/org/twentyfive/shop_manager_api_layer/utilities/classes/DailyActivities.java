package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyActivities {
    private LocalDate rawDate;
    private String dailyCost;
    private String dailyTotalRevenue;
    private List<DailyCashRegister> cashRegisters;
    //TODO lista della spesa

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return rawDate.format(formatter);
    }

    public String getDailyTotalRevenue() {
        double sum = cashRegisters.stream()
                .mapToDouble(register -> {
                    try {
                        // Converte la stringa `total` in double
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                        return df.parse(register.getTotal()).doubleValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0.0;
                    }
                })
                .sum();

        // Converte dailyCost in double (se non è null)
        double cost = 0.0;
        if (dailyCost != null && !dailyCost.isEmpty()) {
            try {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                cost = df.parse(dailyCost).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Calcola il totale netto
        double netTotal = sum - cost;

        // Formatta il risultato
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(netTotal);
    }


    public void setDailyCost(double dailyCost) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        this.dailyCost = df.format(dailyCost);
    }
}
