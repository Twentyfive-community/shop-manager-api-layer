package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteCustomerRes;
import org.twentyfive.shop_manager_api_layer.exceptions.CustomerNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.CustomerMapperService;
import org.twentyfive.shop_manager_api_layer.models.Customer;
import org.twentyfive.shop_manager_api_layer.repositories.CustomerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleCustomer;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

//    private final BusinessService businessService;

    private final CustomerMapperService customerMapperService;


    public Page<SimpleCustomer> getAll(Long id, int page, int size, String companyName) {
        List<Customer> customers = customerRepository.findByBusinessIdAndCompanyNameContainsIgnoreCaseAndDisabledFalseOrderByCompanyNameAsc(id,companyName);
        List<SimpleCustomer> simpleCustomers = customerMapperService.mapListCustomerToListSimpleCustomers(customers);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(simpleCustomers, pageable);
    }

    public Customer getByIdAndCompanyName(Long id, String companyName) {
        return customerRepository.findByBusinessIdAndCompanyName(id,companyName).orElseThrow(() -> new CustomerNotFoundException("Can't find Customer with this company name = "+companyName+
                " and this business id:" +id));
    }

    @Transactional
    public Boolean add(Long id, AddCustomerReq addCustomerReq) {
        if (existsByBusinessIdAndCompanyNameAndDisabledTrue(id, addCustomerReq.getCompanyName())){

            Customer customer = getByIdAndCompanyName(id, addCustomerReq.getCompanyName());
            return customerMapperService.enableAndUpdateCustomerFromAddCustomerReq(customer, addCustomerReq) != null;
        }

//        Business business = businessService.getById(id);
        Business business = null;
        return customerMapperService.createCustomerFromAddSupplierReq(addCustomerReq,business) != null;
    }

    @Transactional
    public Boolean deleteByCompanyName(Long id, String name) {

        Customer customer = getByIdAndCompanyName(id, name);
        customer.setDisabled(true);

        return customerRepository.save(customer) != null;
    }

    public List<GetAutoCompleteCustomerRes> search(Long id, String value) {
        List<Customer> customers = customerRepository.findByBusinessIdAndCompanyNameContainsIgnoreCaseAndDisabledFalseOrderByCompanyNameAsc(id, value);

        return customers.stream().map(customer -> new GetAutoCompleteCustomerRes(customer.getCompanyName())).toList();
    }

    private Boolean existsByBusinessIdAndCompanyNameAndDisabledTrue(Long id, String name) {
        return customerRepository.existsByBusinessIdAndCompanyNameAndDisabledTrue(id,name);
    }

}
