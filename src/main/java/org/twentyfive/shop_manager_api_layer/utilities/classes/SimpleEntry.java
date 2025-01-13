package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEntry {
    private String label;
    private double value;
}
