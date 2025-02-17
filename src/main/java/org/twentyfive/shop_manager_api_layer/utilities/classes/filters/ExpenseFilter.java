package org.twentyfive.shop_manager_api_layer.utilities.classes.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseFilter extends GenericFilter{

    private String paymentMethod;
    private Boolean paid;
}
