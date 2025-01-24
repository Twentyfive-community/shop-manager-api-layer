package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuppliersAndPaymentMethods {
    private List<String> suppliers;
    private List<String> paymentMethods;
}
