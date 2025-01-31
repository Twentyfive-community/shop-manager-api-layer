package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.SupplierMapperService;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final BusinessService businessService;

    private final SupplierMapperService supplierMapperService;
    private final SupplierRepository supplierRepository;

    public boolean add(Long id,AddSupplierReq addSupplierReq) {
        Business business = businessService.getById(id);
        return supplierMapperService.createSupplierFromNameAndAddressAndBusiness(addSupplierReq.getName(), addSupplierReq.getAddress(), business) != null;
    }

    public Boolean addList(Long id,List<AddSupplierReq> addSupplierReqList) {
        Business business = businessService.getById(id);
        for (AddSupplierReq addSupplierReq : addSupplierReqList) {
            supplierMapperService.createSupplierFromNameAndAddressAndBusiness(addSupplierReq.getName(), addSupplierReq.getAddress(), business);
        }
        return true;
    }

    public Page<String> getAll(Long id,int page, int size) {
        List<String> supplierNames = supplierRepository.findSupplierNamesByBusinessId(id);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(supplierNames,pageable);
    }
    public List<String> search(Long id, String value){
        return supplierRepository.findSupplierNamesByBusinessIdAndValue(id, value);

    }

    public Supplier getByIdAndName(Long businessId, String name){
        return supplierRepository.findByBusinessIdAndName(businessId,name).
                orElseThrow(() -> new SupplierNotFoundException("Supplier not found with this businessId: "+businessId+" and name: "+name));
    }
}
