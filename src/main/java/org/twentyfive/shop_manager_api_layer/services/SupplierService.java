package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
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

import java.io.IOException;
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
    private final MsUserClient msUserClient;

    @Transactional
    public boolean add(AddSupplierReq addSupplierReq,String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        Long id = business.getId();
        SupplierGroup supplierGroup = supplierGroupService.optFindByBusinessIdAndName(id, addSupplierReq.getGroupName()).orElse(null);

        if (existsByBusinessIdAndNameAndDisabledTrue(id, addSupplierReq.getName())){
            Supplier supplier = getByIdAndName(id, addSupplierReq.getName());
            supplier.setGroup(supplierGroup);
            supplier.setDisabled(false);
            return supplierRepository.save(supplier) != null;
        }

        return supplierMapperService.createSupplierFromAddSupplierReq(addSupplierReq.getName(), supplierGroup,business) != null;
    }

    @Transactional
    public Boolean addGroup(String authorization, AddSupplierGroupReq addSupplierGroupReq) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        SupplierGroup supplierGroup;
        List<Supplier> suppliers = getAllByBusinessIdAndNameList(business.getId(), addSupplierGroupReq.getSupplierNames());

        if (supplierGroupService.existsByBusinessIdAndName(business.getId(), addSupplierGroupReq.getName())){
            supplierGroup = supplierGroupService.findByBusinessIdAndName(business.getId(), addSupplierGroupReq.getName());
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
    public Boolean addList(List<AddSupplierReq> addSupplierReqList,
                           String authorization) throws IOException {

        for (AddSupplierReq addSupplierReq : addSupplierReqList) {
            add(addSupplierReq,authorization);
        }

        return true;
    }

    public Page<SimpleSupplier> getAll(String authorization, int page, int size, String name) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<Supplier> suppliers = supplierRepository.findByBusinessIdAndNameContainsIgnoreCaseAndDisabledFalseOrderByNameAsc(business.getId(),name);
        List<SimpleSupplier> simpleSuppliers = supplierMapperService.mapListSupplierToListSimpleSupplier(suppliers);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(simpleSuppliers,pageable);
    }

    public List<Supplier> getAllByBusinessIdAndNameList(Long businessId, List<String> supplierNames){
        return supplierRepository.findAllByBusinessIdAndNameInAndDisabledFalse(businessId, supplierNames);
    }

    public List<GetAutoCompleteSupplierRes> search(String authorization, String value) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<Supplier> suppliers = supplierRepository.findSuppliersByBusinessIdAndValueAndDisabledFalse(business.getId(), value);

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

    public List<String> getAllNames(String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        return supplierRepository.findAllNamesByIdAndDisabledFalse(business.getId());
    }


    public List<String> searchGroups(String authorization, String value) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        return supplierGroupRepository.findSupplierGroupNamesByBusinessIdAndValue(business.getId(), value);
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
    public Boolean deleteByName(String authorization, String name) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        Supplier supplier = getByIdAndName(business.getId(), name);

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
    public Boolean update(String authorization, UpdateSupplierReq updateSupplierReq) throws IOException {
        Long id = msUserClient.getBusinessFromToken(authorization).getId();
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
    public Boolean deleteGroup(String authorization, String name) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        SupplierGroup group = supplierGroupService.findByBusinessIdAndName(business.getId(),name);

        List<Supplier> suppliers = group.getSuppliers();
        for (Supplier supplier : suppliers) {
            supplier.setGroup(null);
        }

        supplierRepository.saveAll(suppliers);

        supplierGroupRepository.delete(group);

        return true;
    }

    public Page<SimpleSupplierGroup> getAllGroups(String authorization, int page, int size,String name) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        Pageable pageable = PageRequest.of(page, size);

        List<SupplierGroup> supplierGroups = supplierGroupRepository.findAllByBusiness_IdAndNameContainsIgnoreCase(business.getId(),name);

        List<SimpleSupplierGroup> simpleSupplierGroups = supplierMapperService.mapListSimpleSupplierGroupFromListSupplierGroup(supplierGroups);

        return PageUtility.convertListToPage(simpleSupplierGroups,pageable);
    }


    public GetSupplierWithoutGroupReq getSupplierWithoutGroup(String authorization, String name, String value) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<SupplierAndGroupCheck> supplierAndGroupChecks = supplierRepository.getAllNamesByBusiness_IdAndGroupNullOrNameAndFilterNameAndDisabledFalse(business.getId(), name, value);
        return new GetSupplierWithoutGroupReq(supplierAndGroupChecks);

    }
}
