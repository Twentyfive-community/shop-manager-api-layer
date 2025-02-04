package org.twentyfive.shop_manager_api_layer.utilities.classes.simples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.LabelAndValue;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Operation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleComposedEntryClosure {
    private String composedLabelEntry;
    private Operation operation;
    private List<LabelAndValue> labelAndValues;

    public double getTotal(){
        double total = 0;
        for (LabelAndValue labelAndValue : labelAndValues) {
            total += labelAndValue.getValue();
        }
        return total;
    }
}
