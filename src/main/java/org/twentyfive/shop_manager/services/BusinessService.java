package org.twentyfive.shop_manager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager.exceptions.BusinessNotFoundException;
import org.twentyfive.shop_manager.models.Business;
import org.twentyfive.shop_manager.models.Worker;
import org.twentyfive.shop_manager.repositories.BusinessRepository;

import java.util.List;

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
