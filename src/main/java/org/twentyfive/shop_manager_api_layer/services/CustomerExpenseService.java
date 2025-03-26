package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.BusinessUserClient;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.CustomerExpenseMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.repositories.CustomerExpenseRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CustomerExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.BusinessUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerExpenseService {

    private final CustomerExpenseMapperService customerExpenseMapperService;

    private final CustomerService customerService;
//    private final BusinessWorkerService businessWorkerService;

    private final CustomerExpenseRepository customerExpenseRepository;
    private final BusinessUserClient businessUserClient;
    private final MsUserClient msUserClient;


    public Page<CustomerExpenseDTO> getPeriodExpenses(String authorization, int page, int size, DateRange dateRange, String value) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<CustomerExpense> customerExpenses = customerExpenseRepository.findByWorker_Business_IdAndCustomer_CompanyNameContainsAndRefTimeBetweenOrderByRefTimeDesc(business.getId(),value, dateRange.getStart(), dateRange.getEnd());
        List<CustomerExpenseDTO> expenseDTOS = customerExpenseMapperService.mapListCustomerExpensesToListCustomerExpensesDTO(customerExpenses);

        Pageable pageable = PageRequest.of(page, size);

        return PageUtility.convertListToPage(expenseDTOS, pageable);
    }

    public List<CustomerExpense> getAllByDateBetween(Long id, DateRange dateRange) {
        return customerExpenseRepository.findByWorker_Business_IdAndRefTimeBetween(id,dateRange.getStart(),dateRange.getEnd());
    }
    @Transactional
    public Boolean add(AddCustomerExpenseReq addCustomerExpenseReq,
                       String authorization) throws IOException {
        CustomerExpense customerExpense = createCustomerExpenseFromAddCustomerExpenseReq(addCustomerExpenseReq,authorization);

        return customerExpenseRepository.save(customerExpense) != null;
    }

    @Transactional
    public boolean update(UpdateCustomerExpenseReq updateCustomerExpenseReq,
                          String authorization) throws IOException {
        customerExpenseRepository.findById(updateCustomerExpenseReq.getId()).orElseThrow(() -> new ExpenseNotFoundException("Non esiste una spesa con questo id! : "+updateCustomerExpenseReq.getId()));

        CustomerExpense customerExpense = createCustomerExpenseFromAddCustomerExpenseReq(updateCustomerExpenseReq.getAddCustomerExpenseReq(),authorization);
        customerExpense.setId(updateCustomerExpenseReq.getId());

        return customerExpenseRepository.save(customerExpense) != null;
    }

    @Transactional
    public Boolean delete(Long id) {
        if(!customerExpenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Non esiste una spesa con questo id: "+id);
        }
        customerExpenseRepository.deleteById(id);
        return true;
    }

    private CustomerExpense createCustomerExpenseFromAddCustomerExpenseReq(AddCustomerExpenseReq addCustomerExpenseReq,String authorization) throws IOException {

        BusinessUser businessUser = businessUserClient.getBusinessUserFromToken(authorization);
        Customer customer = customerService.getByIdAndCompanyName(businessUser.getBusiness().getId(), addCustomerExpenseReq.getCompanyName());

        CustomerExpense customerExpense = new CustomerExpense();

        customerExpense.setCustomer(customer);
        customerExpense.setWorker(businessUser);

        customerExpense.setValue(addCustomerExpenseReq.getValue());
        customerExpense.setPaymentMethod(PaymentMethod.fromValue(addCustomerExpenseReq.getPaymentMethod()));
        customerExpense.setRefTime(addCustomerExpenseReq.getRefTime());
        customerExpense.setIssued(addCustomerExpenseReq.isIssued());
        customerExpense.setBuyTime(LocalDateTime.now());

        return customerExpense;
    }
}
