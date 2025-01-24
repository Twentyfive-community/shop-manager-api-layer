package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseMapperService {

    public List<ExpenseDTO> mapListExpensesToListExpensesDTO(List<Expense> expenses) {
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for (Expense expense : expenses) {
            ExpenseDTO expenseDTO = mapExpenseToExpenseDTO(expense);
            expenseDTOs.add(expenseDTO);
        }
        return expenseDTOs;
    }

    private ExpenseDTO mapExpenseToExpenseDTO(Expense expense) {
        ExpenseDTO expenseDTO = new ExpenseDTO();

        expenseDTO.setId(expense.getId());
        expenseDTO.setRefTime(expense.getRefTime());
        expenseDTO.setDoneBy(expense.getWorker().getId().getWorker().getFullName());
        expenseDTO.setPaymentMethod(expense.getPaymentMethod().getValue());
        expenseDTO.setSupplier(expense.getSupplier().getName());
        expenseDTO.setValue(expense.getValue());

        return expenseDTO;
    }
}
