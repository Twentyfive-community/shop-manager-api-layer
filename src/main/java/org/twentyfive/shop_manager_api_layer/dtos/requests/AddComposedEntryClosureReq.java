package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.LabelAndValue;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddComposedEntryClosureReq {
    private String composedLabelEntry;
    private List<LabelAndValue> labelAndValues;
}
