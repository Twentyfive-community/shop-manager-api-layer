package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleComposedEntry;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleEntry;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCashRegisterReq {
    private Long businessId;
    private String timeSlotName;
    private List<SimpleEntry> entries;
    private List<SimpleComposedEntry> composedEntries;
    private CashRegister cashRegister;

}
