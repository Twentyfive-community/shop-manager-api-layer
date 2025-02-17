package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerReq;
import org.twentyfive.shop_manager_api_layer.exceptions.CustomerAlreadyExistsException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Customer;
import org.twentyfive.shop_manager_api_layer.repositories.CustomerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleCustomer;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerMapperService {

    private final CustomerRepository customerRepository;

    public List<SimpleCustomer> mapListCustomerToListSimpleCustomers(List<Customer> customers) {
        List<SimpleCustomer> simpleCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            SimpleCustomer simpleCustomer = mapCustomerToSimpleCustomer(customer);
            simpleCustomers.add(simpleCustomer);
        }
        return simpleCustomers;
    }

    private SimpleCustomer mapCustomerToSimpleCustomer(Customer customer) {
        return new SimpleCustomer(
                customer.getId(),
                customer.getCompanyName() != null ? customer.getCompanyName() : "-",
                customer.getRegisteredOffice() != null ? customer.getRegisteredOffice() : "-",
                customer.getVatNumber() != null ? customer.getVatNumber() : "-",
                customer.getPec() != null ? customer.getPec() : "-",
                customer.getEmail() != null ? customer.getEmail() : "-",
                customer.getSdi() != null ? customer.getSdi() : "-"
        );
    }

    public Customer createCustomerFromAddSupplierReq(AddCustomerReq addCustomerReq, Business business) {
        Customer customer = new Customer();

        customer.setId(addCustomerReq.getId());
        customer.setCompanyName(addCustomerReq.getCompanyName());
        customer.setBusiness(business);
        customer.setRegisteredOffice(addCustomerReq.getRegisteredOffice());
        customer.setVatNumber(addCustomerReq.getVatNumber());
        customer.setPec(addCustomerReq.getPec());
        customer.setEmail(addCustomerReq.getEmail());
        customer.setSdi(addCustomerReq.getSdi());

        if (!(customerRepository.existsByBusiness_IdAndCompanyName(business.getId(), addCustomerReq.getCompanyName()))) {
            return customerRepository.save(customer);
        }

        throw new CustomerAlreadyExistsException("Customer does exist with this company name " + addCustomerReq.getCompanyName() + " and businessId: " + business.getId());
    }

    public Boolean enableAndUpdateCustomerFromAddCustomerReq(Customer customer, AddCustomerReq addCustomerReq) {
        customer.setPec(addCustomerReq.getPec());
        customer.setDisabled(false);
        customer.setEmail(addCustomerReq.getEmail());
        customer.setSdi(addCustomerReq.getSdi());
        customer.setVatNumber(addCustomerReq.getVatNumber());
        customer.setRegisteredOffice(addCustomerReq.getRegisteredOffice());

        return customerRepository.save(customer) != null;
    }
}
