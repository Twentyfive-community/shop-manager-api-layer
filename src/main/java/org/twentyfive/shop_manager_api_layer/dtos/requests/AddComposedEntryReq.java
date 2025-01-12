package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddComposedEntryReq {
    private ComposedEntry composedEntry;
    private List<String> entryNames;
}
