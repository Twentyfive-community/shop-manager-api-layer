package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntryClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntryClosure;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCashRegisterReq {
    private Long businessId;
    private String timeSlotName;
    private LocalDate cashRegisterDate;
    private List<AddEntryClosureReq> entries;
    private List<AddComposedEntryClosureReq> composedEntries;

}
