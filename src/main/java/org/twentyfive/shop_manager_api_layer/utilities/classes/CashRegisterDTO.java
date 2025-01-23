package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.models.EntryClosure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterDTO {
    private Long id;
    private LocalDate refTime;
    private List<SimpleEntryClosure> entryClosures;
    private List<SimpleComposedEntryClosure> composedEntryClosures;

    private String closedBy;

    private String updatedBy;
    private LocalDateTime updatedTime; //tempo ultima chiusura o update della chiusura cassa


    public String getFormattedRefTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ITALIAN);
        return refTime.format(formatter);
    }

    public String getFormattedUpdatedTime() {
        if (updatedTime == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm", Locale.ITALIAN);
        return updatedTime.format(formatter);
    }

    private Report getReport(){
        Report report = new Report();
        for (SimpleEntryClosure entryClosure : entryClosures) {
            if (entryClosure.getOperation().getSymbol().equals("+")){
                report.setTotalRevenue(report.getTotalRevenue()+entryClosure.getValue());
            } else if (entryClosure.getOperation().getSymbol().equals("-")){
                report.setTotalCost(report.getTotalCost()+entryClosure.getValue());
            }
        }
        for (SimpleComposedEntryClosure composedEntryClosure : composedEntryClosures) {
            if (composedEntryClosure.getOperation().getSymbol().equals("+")){
                report.setTotalRevenue(report.getTotalRevenue()+ composedEntryClosure.getTotal());
            } else if (composedEntryClosure.getOperation().getSymbol().equals("-")){
                report.setTotalCost(report.getTotalCost() + composedEntryClosure.getTotal());
            }
        }
        return report;
    }
}
