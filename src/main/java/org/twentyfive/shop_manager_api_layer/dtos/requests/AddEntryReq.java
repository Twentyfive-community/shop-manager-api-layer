package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Operation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEntryReq {
    private String label;
    private Operation operation;
}
