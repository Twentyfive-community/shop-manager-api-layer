package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAllPaymentMethodRes;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;

@Service
@RequiredArgsConstructor
public class BusinessService {


    private final BusinessRepository businessRepository;


    public Business getById(Long id) {
        return businessRepository.findById(id).orElseThrow(()->new BusinessNotFoundException("Business not found with id: " + id));
    }

    public Boolean add(AddBusinessReq addBusinessReq) {
        Business business = new Business();
        business.setName(addBusinessReq.getName());
        businessRepository.save(business);
        return true;
    }

    public GetAllPaymentMethodRes getAllPaymentMethod() {
        return new GetAllPaymentMethodRes(PaymentMethod.getValues());
    }
}
