package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.ids.ComposedEntryClosureId;
import org.twentyfive.shop_manager_api_layer.utilities.classes.LabelAndValue;
import org.twentyfive.shop_manager_api_layer.utilities.converters.LabelAndValueConverter;

import java.util.List;

@Entity
@Table(name ="composed_entry_closures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComposedEntryClosure {

    @EmbeddedId
    private ComposedEntryClosureId id;  // Chiave primaria composta

    @Column(name = "value", columnDefinition = "TEXT") // Persistenza JSON in formato testo
    @Convert(converter = LabelAndValueConverter.class) // Convertitore per salvare la lista come JSON
    private List<LabelAndValue> value;

    public double getTotalValue() {
        double total = 0;
        for (LabelAndValue labelAndValue : this.value) {
            if (labelAndValue.getValue() != null) { // Controllo di null
                total += labelAndValue.getValue();
            }
        }
        return total;
    }
}
