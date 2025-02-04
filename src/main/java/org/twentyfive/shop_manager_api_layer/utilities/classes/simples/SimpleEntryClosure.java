package org.twentyfive.shop_manager_api_layer.utilities.classes.simples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Operation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEntryClosure {
    private String label;
    private double value;
    private Operation operation;
}
