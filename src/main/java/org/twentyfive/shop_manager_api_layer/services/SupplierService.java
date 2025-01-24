package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.mappers.SupplierMapperService;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final BusinessService businessService;

    private final SupplierMapperService supplierMapperService;
    private final SupplierRepository supplierRepository;

    public boolean add(AddSupplierReq addSupplierReq) {
        Business business = businessService.getById(addSupplierReq.getBusinessId());
        return supplierMapperService.createSupplierFromNameAndAddressAndBusiness(addSupplierReq.getName(), addSupplierReq.getAddress(), business) != null;
    }

    public List<String> getAllByBusinessId(Long id) {
        return supplierRepository.findSupplierNamesByBusinessId(id);
    }
}
