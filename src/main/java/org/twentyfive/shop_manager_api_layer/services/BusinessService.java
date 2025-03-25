package org.twentyfive.shop_manager_api_layer.services;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAllPaymentMethodRes;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;

@Service
public class BusinessService {

    public GetAllPaymentMethodRes getAllPaymentMethod() {
        return new GetAllPaymentMethodRes(PaymentMethod.getValues());
    }
}
