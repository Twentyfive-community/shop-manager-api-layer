package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.BusinessSupplier;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessSupplierId;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessRepository;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessSupplierRepository;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;



@Service
@RequiredArgsConstructor
public class SupplierMapperService {
    private final SupplierRepository supplierRepository;
    private final BusinessSupplierRepository businessSupplierRepository;

    public Supplier createSupplierFromNameAndAddressAndBusiness(String name, String address, Business business) {
        // Crea il fornitore
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setAddress(address);

        Supplier supplierSaved = supplierRepository.save(supplier);

        BusinessSupplier businessSupplier = new BusinessSupplier();
        BusinessSupplierId businessSupplierId = new BusinessSupplierId();
        businessSupplierId.setBusiness(business);
        businessSupplierId.setSupplier(supplierSaved);

        businessSupplier.setId(businessSupplierId);

        businessSupplierRepository.save(businessSupplier);

        return supplierSaved;
    }

}
