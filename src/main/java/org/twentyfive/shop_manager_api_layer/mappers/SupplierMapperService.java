package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.models.SupplierGroup;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplier;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplierGroup;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SupplierMapperService {

    private final SupplierRepository supplierRepository;

    public Supplier createSupplierFromAddSupplierReq(String name, SupplierGroup supplierGroup,Business business) {
        // Crea il fornitore
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setBusiness(business);
        supplier.setGroup(supplierGroup);
        Supplier supplierSaved = supplierRepository.save(supplier);

        return supplierSaved;
    }

    public List<SimpleSupplier> mapListSupplierToListSimpleSupplier(List<Supplier> suppliers) {
        List<SimpleSupplier> simpleSuppliers = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SimpleSupplier simpleSupplier = mapSupplierToSimpleSupplier(supplier);
            simpleSuppliers.add(simpleSupplier);
        }
        return simpleSuppliers;
    }

    private SimpleSupplier mapSupplierToSimpleSupplier(Supplier supplier) {
        return new SimpleSupplier(supplier.getId(), supplier.getName(),supplier.getGroup() != null ? supplier.getGroup().getName() : "-");
    }

    public SupplierGroup mapSupplierGroupFromBusinessAndSuppliers(String name,Business business, List<Supplier> suppliers) {
        SupplierGroup supplierGroup = new SupplierGroup();
        supplierGroup.setBusiness(business);
        supplierGroup.setName(name);
        supplierGroup.setSuppliers(suppliers);
        return supplierGroup;
    }

    public List<SimpleSupplierGroup> mapListSimpleSupplierGroupFromListSupplierGroup(List<SupplierGroup> supplierGroups) {
        List<SimpleSupplierGroup> simpleSupplierGroups = new ArrayList<>();

        for (SupplierGroup supplierGroup : supplierGroups) {
            SimpleSupplierGroup simpleSupplierGroup = mapSimpleSupplierGroupFromSupplierGroup(supplierGroup);
            simpleSupplierGroups.add(simpleSupplierGroup);
        }
        return simpleSupplierGroups;
    }

    private SimpleSupplierGroup mapSimpleSupplierGroupFromSupplierGroup(SupplierGroup supplierGroup) {
        SimpleSupplierGroup simpleSupplierGroup = new SimpleSupplierGroup();

        simpleSupplierGroup.setName(supplierGroup.getName());
        simpleSupplierGroup.setSuppliers(mapListSupplierToListSimpleSupplier(supplierGroup.getSuppliers()));
        return simpleSupplierGroup;

    }
}
