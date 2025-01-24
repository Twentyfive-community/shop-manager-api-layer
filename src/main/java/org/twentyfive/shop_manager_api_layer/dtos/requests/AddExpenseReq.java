package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddExpenseReq {
    private String paymentMethod;
    private double value;

    private Long businessId;
    private String supplierName;


}
