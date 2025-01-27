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
    private List<DailyCashRegister> cashRegisters;
    //TODO lista della spesa

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return rawDate.format(formatter);
    }

    public String getDailyTotalRevenue(){
        double sum = cashRegisters.stream()
                .mapToDouble(register -> {
                    try {
                        // Converte la stringa `total` in double
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                        return df.parse(register.getTotal()).doubleValue();
                    } catch (Exception e) {
                        // Gestione errori nel parsing (ad esempio se `total` è null o malformattato)
                        e.printStackTrace();
                        return 0.0;
                    }
                })
                .sum();
        // Formatta il risultato e assegna a `dailyTotalRevenue`
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(sum);
    }
}
