package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessSupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessSupplierService {
    private final BusinessSupplierRepository businessSupplierRepository;

    public Page<String> getAll(Long id, int page, int size) {
        List<String> supplierNames = businessSupplierRepository.findSupplierNamesByBusinessIdAndDisabledFalse(id);

        Pageable pageable = PageRequest.of(page, size);

        return PageUtility.convertListToPage(supplierNames, pageable);

    }


    public List<String> search(Long id, String value) {
        return businessSupplierRepository.findSupplierNamesByBusinessIdAndValueAndDisabledFalse(id,value);
    }
}
