package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComposedEntryClosureDetails {
    private String label;
    private List<LabelAndValueDetails> labelAndValues;

    public void setLabelAndValues(List<LabelAndValue> labelAndValues){
        List<LabelAndValueDetails> labelAndValuesDetails = new ArrayList<>();
        for (LabelAndValue labelAndValue : labelAndValues) {
            LabelAndValueDetails labelAndValueDetails = new LabelAndValueDetails();
            labelAndValueDetails.setLabel(labelAndValue.getLabel());
            labelAndValueDetails.setValue(labelAndValue.getValue());
            labelAndValuesDetails.add(labelAndValueDetails);
        }
        this.labelAndValues = labelAndValuesDetails;
    }
}
