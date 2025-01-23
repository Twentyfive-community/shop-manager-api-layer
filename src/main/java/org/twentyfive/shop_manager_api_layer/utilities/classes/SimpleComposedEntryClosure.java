package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleComposedEntryClosure {
    private String composedLabelEntry;
    private List<LabelAndValue> labelAndValues;
    private double totalValue;
}
