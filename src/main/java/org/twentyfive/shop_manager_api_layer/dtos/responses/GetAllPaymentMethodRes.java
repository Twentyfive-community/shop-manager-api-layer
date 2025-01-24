package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPaymentMethodRes {
    private List<String> paymentMethods;
}
