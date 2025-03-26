package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddExpenseReq {

    private LocalDate refTime;
    private String paymentMethod;

    private double value;
    private boolean paid;

    private String supplierName;


}
