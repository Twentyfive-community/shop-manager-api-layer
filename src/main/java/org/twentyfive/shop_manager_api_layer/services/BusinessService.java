package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessRepository;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    public Business getById(Long id) {
        return businessRepository.findById(id).orElseThrow(()->new BusinessNotFoundException("Business not found"));
    }

    public Boolean add(Business business) {
        businessRepository.save(business);
        return true;
    }

}
