package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.CustomerExpense;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CustomerExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerExpenseMapperService {

    public List<CustomerExpenseDTO> mapListCustomerExpensesToListCustomerExpensesDTO(List<CustomerExpense> customerExpenses) {
        List<CustomerExpenseDTO> expenseDTOs = new ArrayList<>();
        for (CustomerExpense customerExpense : customerExpenses) {
            CustomerExpenseDTO customerExpenseDTO = mapCustomerExpenseToCustomerExpenseDTO(customerExpense);
            expenseDTOs.add(customerExpenseDTO);
        }
        return expenseDTOs;
    }

    private CustomerExpenseDTO mapCustomerExpenseToCustomerExpenseDTO(CustomerExpense customerExpense) {
        CustomerExpenseDTO customerExpenseDTO = new CustomerExpenseDTO();

        customerExpenseDTO.setId(customerExpense.getId());
        customerExpenseDTO.setRefTime(customerExpense.getRefTime());
        customerExpenseDTO.setDoneBy(customerExpense.getWorker().getId().getWorker().getFullName());
        customerExpenseDTO.setIssued(customerExpense.isIssued());
        customerExpenseDTO.setPaymentMethod(customerExpense.getPaymentMethod().getValue());
        customerExpenseDTO.setCustomer(customerExpense.getCustomer().getCompanyName());
        customerExpenseDTO.setValue(customerExpense.getValue());

        return customerExpenseDTO;
    }
}
