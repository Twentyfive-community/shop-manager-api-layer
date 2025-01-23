package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.models.EntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntryClosure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCashRegisterByDateAndTimeSlotRes {
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
}
