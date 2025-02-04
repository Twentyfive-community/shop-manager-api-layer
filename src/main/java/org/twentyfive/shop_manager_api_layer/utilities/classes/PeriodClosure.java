package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodClosure {
    private String period;
    //Attualmente tiene in conto solo della chiusura cassa finale di ogni attività!
    private String totalClosingReceipts;
    private String totalClosurePos;
    private String total;

    public PeriodClosure(double totalClosingReceipts, double totalClosurePos,DateRange dateRange) {
        double total = totalClosingReceipts - totalClosurePos;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        String s1 = dateRange.getStart().format(formatter);
        String s2 = dateRange.getEnd().format(formatter);

        this.period = s1 + " - " + s2;
        this.totalClosingReceipts = df.format(totalClosingReceipts);
        this.totalClosurePos = df.format(totalClosurePos);
        this.total = df.format(total);

    }

    public void setPeriod(LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String s1 = start.format(formatter);
        String s2 = end.format(formatter);

        this.period = s1 + " - " + s2;
    }

}
