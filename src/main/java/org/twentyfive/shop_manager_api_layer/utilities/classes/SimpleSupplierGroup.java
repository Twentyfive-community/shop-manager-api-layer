package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSupplierGroup {
    private String name;

    private List<SimpleSupplier> suppliers;

    public int setCountSupplier(){
        return this.suppliers.size();
    }
}
