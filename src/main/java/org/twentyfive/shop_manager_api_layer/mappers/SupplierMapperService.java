package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;



@Service
@RequiredArgsConstructor
public class SupplierMapperService {
    private final SupplierRepository supplierRepository;

    public Supplier createSupplierFromNameAndAddressAndBusiness(String name, String address, Business business) {
        // Crea il fornitore
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setBusiness(business);
        Supplier supplierSaved = supplierRepository.save(supplier);

        return supplierSaved;
    }

}
