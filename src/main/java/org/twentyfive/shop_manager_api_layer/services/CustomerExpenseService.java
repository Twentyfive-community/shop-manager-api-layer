package org.twentyfive.shop_manager_api_layer.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.CustomerExpenseMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.repositories.CustomerExpenseRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CustomerExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerExpenseService {

    private final CustomerExpenseMapperService customerExpenseMapperService;

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


    public Page<CustomerExpenseDTO> getPeriodExpenses(Long id, int page, int size, DateRange dateRange) {
        List<CustomerExpense> customerExpenses = customerExpenseRepository.findByWorker_Id_Business_IdAndRefTimeBetweenOrderByRefTimeDesc(id, dateRange.getStart(), dateRange.getEnd());
        List<CustomerExpenseDTO> expenseDTOS = customerExpenseMapperService.mapListCustomerExpensesToListCustomerExpensesDTO(customerExpenses);

        Pageable pageable = PageRequest.of(page, size);

        return PageUtility.convertListToPage(expenseDTOS, pageable);
    }

    public boolean update(UpdateCustomerExpenseReq updateCustomerExpenseReq) throws IOException {
        customerExpenseRepository.findById(updateCustomerExpenseReq.getId()).orElseThrow(() -> new ExpenseNotFoundException("Non esiste una spesa con questo id! : "+updateCustomerExpenseReq.getId()));

        CustomerExpense customerExpense = createCustomerExpenseFromAddCustomerExpenseReq(updateCustomerExpenseReq.getAddCustomerExpenseReq());
        customerExpense.setId(updateCustomerExpenseReq.getId());

        return customerExpenseRepository.save(customerExpense) != null;
    }

    public Boolean delete(Long id) {
        if(!customerExpenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Non esiste una spesa con questo id: "+id);
        }
        customerExpenseRepository.deleteById(id);
        return true;
    }
}
