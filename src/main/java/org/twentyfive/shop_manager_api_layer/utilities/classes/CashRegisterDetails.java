package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterDetails {
    private Long id;
    private String timeSlotName;
    private String refTime;

    private List<EntryClosureDetails> entryClosureDetails;
    private List<ComposedEntryClosureDetails> composedEntryClosureDetails;

    private String totalRevenue;
    private String totalCost;
    private String total;

    private String firstModifiedWorker;
    private String firstModifiedDate;

    private String lastModifiedWorker;
    private String lastModifiedDate;

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

    public void setRefTime(LocalDate refTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.refTime = refTime.format(formatter);
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.lastModifiedDate = lastModifiedDate.format(formatter);
    }

    public void setFirstModifiedDate(LocalDateTime firstModifiedDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.firstModifiedDate = firstModifiedDate.format(formatter);
    }

}
