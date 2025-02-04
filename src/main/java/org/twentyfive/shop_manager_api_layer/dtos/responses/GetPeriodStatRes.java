package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodStatCashRegister;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPeriodStatRes {
    private String period;

    private String periodTotalRevenue;
    private String periodCost;
    private String periodTotal;

    private List<PeriodStatCashRegister> periodStatCashRegisters;

    public String getPeriodCost(){
        double sum = periodStatCashRegisters.stream()
                .mapToDouble(register -> {
                    try {
                        // Converte la stringa total in double
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                        return df.parse(register.getTotalCost()).doubleValue();
                    } catch (Exception e) {
                        // Gestione errori nel parsing (ad esempio se total è null o malformattato)
                        e.printStackTrace();
                        return 0.0;
                    }
                })
                .sum();
        // Formatta il risultato e assegna a dailyTotalRevenue
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(sum);
    }

    public String getPeriodTotalRevenue(){
        double sum = periodStatCashRegisters.stream()
                .mapToDouble(register -> {
                    try {
                        // Converte la stringa total in double
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                        return df.parse(register.getTotalRevenue()).doubleValue();
                    } catch (Exception e) {
                        // Gestione errori nel parsing (ad esempio se total è null o malformattato)
                        e.printStackTrace();
                        return 0.0;
                    }
                })
                .sum();
        // Formatta il risultato e assegna a dailyTotalRevenue
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(sum);
    }

    public String getPeriodTotal(){
        double sum = periodStatCashRegisters.stream()
                .mapToDouble(register -> {
                    try {
                        // Converte la stringa total in double
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
                        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
                        return df.parse(register.getTotal()).doubleValue();
                    } catch (Exception e) {
                        // Gestione errori nel parsing (ad esempio se total è null o malformattato)
                        e.printStackTrace();
                        return 0.0;
                    }
                })
                .sum();
        // Formatta il risultato e assegna a dailyTotalRevenue
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(sum);
    }

    public void setPeriod(LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String s1 = start.format(formatter);
        String s2 = end.format(formatter);

        this.period = s1 + " - " + s2;
    }
}
