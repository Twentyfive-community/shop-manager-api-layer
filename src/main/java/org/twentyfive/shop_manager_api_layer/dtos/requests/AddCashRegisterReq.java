package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCashRegisterReq {
    private String timeSlotName;
    private LocalDate cashRegisterDate;
    private List<AddEntryClosureReq> entries;
    private List<AddComposedEntryClosureReq> composedEntries;

}
