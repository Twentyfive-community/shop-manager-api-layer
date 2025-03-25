package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierGroupReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetSupplierWithoutGroupReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteSupplierRes;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.SupplierMapperService;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.models.SupplierGroup;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierGroupRepository;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SupplierAndGroupCheck;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplierGroup;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplier;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SupplierService {

    //private final BusinessService businessService;
    private final SupplierGroupService supplierGroupService;

    private final SupplierMapperService supplierMapperService;

    private final SupplierGroupRepository supplierGroupRepository;
    private final SupplierRepository supplierRepository;

    @Transactional
    public boolean add(Long id,AddSupplierReq addSupplierReq) {
        SupplierGroup supplierGroup = supplierGroupService.optFindByBusinessIdAndName(id, addSupplierReq.getGroupName()).orElse(null);

        if (existsByBusinessIdAndNameAndDisabledTrue(id, addSupplierReq.getName())){
            Supplier supplier = getByIdAndName(id, addSupplierReq.getName());
            supplier.setGroup(supplierGroup);
            supplier.setDisabled(false);
            return supplierRepository.save(supplier) != null;
        }

        //FIXME
//        Business business = businessService.getById(id);
        Business business = null;
        return supplierMapperService.createSupplierFromAddSupplierReq(addSupplierReq.getName(), supplierGroup,business) != null;
    }

    @Transactional
    public Boolean addGroup(Long id, AddSupplierGroupReq addSupplierGroupReq) {
//        Business business = businessService.getById(id);
        Business business = null;
        SupplierGroup supplierGroup;
        List<Supplier> suppliers = getAllByBusinessIdAndNameList(id, addSupplierGroupReq.getSupplierNames());

        if (supplierGroupService.existsByBusinessIdAndName(id, addSupplierGroupReq.getName())){
            supplierGroup = supplierGroupService.findByBusinessIdAndName(id, addSupplierGroupReq.getName());
            List<Supplier> oldSuppliers = supplierGroup.getSuppliers();

            for (Supplier supplier : oldSuppliers) {
                supplier.setGroup(null);
            }

            supplierRepository.saveAll(oldSuppliers);
        } else {
            supplierGroup = supplierMapperService.mapSupplierGroupFromBusinessAndSuppliers(addSupplierGroupReq.getName(),business,suppliers);
        }

        SupplierGroup save = supplierGroupRepository.save(supplierGroup);

        for (Supplier supplier : suppliers) {
            supplier.setGroup(save);
        }
        supplierRepository.saveAll(suppliers);
        return true;
    }

    @Transactional
    public Boolean addList(Long id,List<AddSupplierReq> addSupplierReqList) {

        for (AddSupplierReq addSupplierReq : addSupplierReqList) {
            add(id,addSupplierReq);
        }

        return true;
    }

    public Page<SimpleSupplier> getAll(Long id, int page, int size, String name) {
        List<Supplier> suppliers = supplierRepository.findByBusinessIdAndNameContainsIgnoreCaseAndDisabledFalseOrderByNameAsc(id,name);
        List<SimpleSupplier> simpleSuppliers = supplierMapperService.mapListSupplierToListSimpleSupplier(suppliers);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(simpleSuppliers,pageable);
    }

    public List<Supplier> getAllByBusinessIdAndNameList(Long businessId, List<String> supplierNames){
        return supplierRepository.findAllByBusinessIdAndNameInAndDisabledFalse(businessId, supplierNames);
    }

    public List<GetAutoCompleteSupplierRes> search(Long businessId, String value) {
        List<Supplier> suppliers = supplierRepository.findSuppliersByBusinessIdAndValueAndDisabledFalse(businessId, value);

        return suppliers.stream()
                .map(supplier -> {
                    String groupName = (supplier.getGroup() != null && supplier.getGroup().getName() != null)
                            ? supplier.getGroup().getName()
                            : null;

                    String formattedName = (groupName != null && !groupName.isEmpty())
                            ? groupName + " - " + supplier.getName()
                            : supplier.getName();

                    return new GetAutoCompleteSupplierRes(formattedName, supplier.getName());
                })
                .collect(Collectors.toList());
    }

    public List<String> getAllNames(Long id) {
        return supplierRepository.findAllNamesByIdAndDisabledFalse(id);
    }


    public List<String> searchGroups(Long id, String value) {
        return supplierGroupRepository.findSupplierGroupNamesByBusinessIdAndValue(id,value);
    }

    public Supplier getById(Long id){
        return supplierRepository.findById(id).
                orElseThrow(() -> new SupplierNotFoundException("Supplier with id " + id + " not found"));
    }

    public Supplier getByIdAndName(Long businessId, String name){
        return supplierRepository.findByBusinessIdAndName(businessId,name).
                orElseThrow(() -> new SupplierNotFoundException("Supplier not found with this businessId: "+businessId+" and name: "+name));
    }

    @Transactional
    public Boolean deleteByName(Long id, String name) {
        Supplier supplier = getByIdAndName(id, name);

        supplier.setDisabled(true);
        supplier.setGroup(null);

        return supplierRepository.save(supplier) != null;
    }


    private Boolean existsByBusinessIdAndNameAndDisabledTrue(Long id, String name) {
        return supplierRepository.existsByBusinessIdAndNameAndDisabledTrue(id,name);
    }

    private Boolean existsByBusinessIdAndSupplierId(Long businessId, Long id) {
        return supplierRepository.existsByBusinessIdAndId(businessId,id);
    }

    @Transactional
    public Boolean update(Long id, UpdateSupplierReq updateSupplierReq) {
        if (existsByBusinessIdAndSupplierId(id,updateSupplierReq.getId())){
            Supplier supplier = getById(updateSupplierReq.getId());
            supplier.setName(updateSupplierReq.getName());
            if (updateSupplierReq.getGroupName() == null || updateSupplierReq.getGroupName().isBlank()){
                supplier.setGroup(null);
            }else if (supplierGroupService.existsByBusinessIdAndName(id,updateSupplierReq.getGroupName())){
                SupplierGroup supplierGroup = supplierGroupService.findByBusinessIdAndName(id,updateSupplierReq.getGroupName());
                supplier.setGroup(supplierGroup);
            }

            return supplierRepository.save(supplier) != null;
        }
        throw new SupplierNotFoundException("Supplier not found with this id: " +updateSupplierReq.getId()+" or it's not associated to this businessId: "+id);
    }

    @Transactional
    public Boolean deleteGroup(Long id, String name) {

        SupplierGroup group = supplierGroupService.findByBusinessIdAndName(id,name);

        List<Supplier> suppliers = group.getSuppliers();
        for (Supplier supplier : suppliers) {
            supplier.setGroup(null);
        }

        supplierRepository.saveAll(suppliers);

        supplierGroupRepository.delete(group);

        return true;
    }

    public Page<SimpleSupplierGroup> getAllGroups(Long id, int page, int size,String name) {
        Pageable pageable = PageRequest.of(page, size);

        List<SupplierGroup> supplierGroups = supplierGroupRepository.findAllByBusiness_IdAndNameContainsIgnoreCase(id,name);

        List<SimpleSupplierGroup> simpleSupplierGroups = supplierMapperService.mapListSimpleSupplierGroupFromListSupplierGroup(supplierGroups);

        return PageUtility.convertListToPage(simpleSupplierGroups,pageable);
    }


    public GetSupplierWithoutGroupReq getSupplierWithoutGroup(Long id, String name, String value) {
        List<SupplierAndGroupCheck> supplierAndGroupChecks = supplierRepository.getAllNamesByBusiness_IdAndGroupNullOrNameAndFilterNameAndDisabledFalse(id, name, value);
        return new GetSupplierWithoutGroupReq(supplierAndGroupChecks);

    }
}
