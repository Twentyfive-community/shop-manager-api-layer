package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessRepository;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SupplierMapperService {
    private final SupplierRepository supplierRepository;
    private final BusinessRepository businessRepository;

    public Supplier createSupplierFromNameAndAddressAndBusiness(String name, String address, Business business) {
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setAddress(address);

        Set<Supplier> suppliers;
        if (business.getSuppliers() == null){
            suppliers = new HashSet<>();
        } else {
            suppliers = business.getSuppliers();
        }


        Supplier supplierSaved = supplierRepository.save(supplier);
        suppliers.add(supplierSaved);

        business.setSuppliers(suppliers);
        businessRepository.save(business);

        return supplierSaved;
    }
}
