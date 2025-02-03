package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.SupplierGroup;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierGroupRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierGroupService {
    private final SupplierGroupRepository supplierGroupRepository;

    public SupplierGroup findByBusinessIdAndName(Long businessId, String name) {
        return supplierGroupRepository.findByBusiness_IdAndName(businessId, name)
                .orElseThrow(() -> new SupplierNotFoundException("SupplierGroup not found with this name: " +name+ " and this businessId: "+businessId));
    }

    public Optional<SupplierGroup> optFindByBusinessIdAndName(Long businessId, String name) {
        return supplierGroupRepository.findByBusiness_IdAndName(businessId, name);
    }

    public Boolean existsByBusinessIdAndName(Long businessId, String name) {
        return supplierGroupRepository.existsByBusiness_IdAndName(businessId, name);
    }

}
