package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateSupplierReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.SupplierMapperService;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleSupplier;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final BusinessService businessService;

    private final SupplierMapperService supplierMapperService;
    private final SupplierRepository supplierRepository;

    public boolean add(Long id,AddSupplierReq addSupplierReq) {

        if (existsByBusinessIdAndName(id, addSupplierReq.getName())){
            Supplier supplier = getByIdAndName(id, addSupplierReq.getName());
            supplier.setDisabled(false);
            return supplierRepository.save(supplier) != null;
        }

        Business business = businessService.getById(id);
        return supplierMapperService.createSupplierFromNameAndAddressAndBusiness(addSupplierReq.getName(), addSupplierReq.getAddress(), business) != null;
    }

    public Boolean addList(Long id,List<AddSupplierReq> addSupplierReqList) {

        for (AddSupplierReq addSupplierReq : addSupplierReqList) {
            add(id,addSupplierReq);
        }

        return true;
    }

    public Page<SimpleSupplier> getAll(Long id, int page, int size) {
        List<Supplier> suppliers = supplierRepository.findByBusinessIdAndDisabledFalseOrderByNameAsc(id);
        List<SimpleSupplier> simpleSuppliers = supplierMapperService.mapListSupplierToListSimpleSupplier(suppliers);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(simpleSuppliers,pageable);
    }

    public List<String> search(Long id, String value){
        return supplierRepository.findSupplierNamesByBusinessIdAndValueAndDisabledFalse(id, value);
    }

    public Supplier getById(Long id){
        return supplierRepository.findById(id).
                orElseThrow(() -> new SupplierNotFoundException("Supplier with id " + id + " not found"));
    }

    public Supplier getByIdAndName(Long businessId, String name){
        return supplierRepository.findByBusinessIdAndName(businessId,name).
                orElseThrow(() -> new SupplierNotFoundException("Supplier not found with this businessId: "+businessId+" and name: "+name));
    }

    public Boolean deleteByName(Long id, String name) {
        Supplier supplier = getByIdAndName(id, name);
        supplier.setDisabled(true);
        return supplierRepository.save(supplier) != null;
    }

    private Boolean existsByBusinessIdAndName(Long id, String name) {
        return supplierRepository.existsByBusinessIdAndName(id,name);
    }

    private Boolean existsByBusinessIdAndSupplierId(Long businessId, Long id) {
        return supplierRepository.existsByBusinessIdAndId(businessId,id);
    }

    public Boolean update(Long id, UpdateSupplierReq updateSupplierReq) {
        if (existsByBusinessIdAndSupplierId(id,updateSupplierReq.getId())){
            Supplier supplier = getById(updateSupplierReq.getId());

            supplier.setName(updateSupplierReq.getName());
            supplier.setAddress(updateSupplierReq.getAddress());

            return supplierRepository.save(supplier) != null;
        }
        throw new ExpenseNotFoundException("Supplier not found with this id: " +updateSupplierReq.getId()+" or it's not associated to this businessId: "+id);
    }
}
