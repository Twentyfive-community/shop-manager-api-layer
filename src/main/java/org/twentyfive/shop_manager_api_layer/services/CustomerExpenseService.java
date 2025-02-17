package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.repositories.CustomerExpenseRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerExpenseService {

    private final CustomerService customerService;
    private final BusinessWorkerService businessWorkerService;

    private final CustomerExpenseRepository customerExpenseRepository;

    @Transactional
    public Boolean add(AddCustomerExpenseReq addCustomerExpenseReq) throws IOException {
        CustomerExpense customerExpense = createCustomerExpenseFromAddCustomerExpenseReq(addCustomerExpenseReq);

        return customerExpenseRepository.save(customerExpense) != null;
    }

    private CustomerExpense createCustomerExpenseFromAddCustomerExpenseReq(AddCustomerExpenseReq addCustomerExpenseReq) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();

        BusinessWorker businessWorker = businessWorkerService.getByBusinessIdAndKeycloakId(addCustomerExpenseReq.getBusinessId(), keycloakId);

        Customer customer = customerService.getByIdAndCompanyName(addCustomerExpenseReq.getBusinessId(), addCustomerExpenseReq.getCompanyName());

        CustomerExpense customerExpense = new CustomerExpense();

        customerExpense.setCustomer(customer);
        customerExpense.setWorker(businessWorker);

        customerExpense.setValue(addCustomerExpenseReq.getValue());
        customerExpense.setPaymentMethod(PaymentMethod.fromValue(addCustomerExpenseReq.getPaymentMethod()));
        customerExpense.setRefTime(addCustomerExpenseReq.getRefTime());
        customerExpense.setIssued(addCustomerExpenseReq.isIssued());
        customerExpense.setBuyTime(LocalDateTime.now());

        return customerExpense;
    }


}
