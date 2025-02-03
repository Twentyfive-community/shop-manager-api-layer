package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.ExpenseMapperService;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.ExpenseRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapperService expenseMapperService;
    private final BusinessWorkerService businessWorkerService;
    private final SupplierService supplierService;

    public Boolean add(AddExpenseReq addExpenseReq) throws IOException {
        Expense expense = createExpenseFromAddExpenseReq(addExpenseReq);
        return expenseRepository.save(expense) != null;
    }

    public boolean update(UpdateExpenseReq updateExpenseReq) throws IOException {
        expenseRepository.findById(updateExpenseReq.getId()).orElseThrow(() -> new ExpenseNotFoundException("Non esiste una spesa con questo id! : "+updateExpenseReq.getId()));

        Expense expense = createExpenseFromAddExpenseReq(updateExpenseReq.getAddExpenseReq());
        expense.setId(updateExpenseReq.getId());

        return expenseRepository.save(expense) != null;
    }

    public List<Expense> getAllByDate(Long id,LocalDate date) {
        return expenseRepository.findByWorker_Id_Business_IdAndRefTime(id,date);
    }

    public double getTotalExpensesByDate(Long id,LocalDate date) {
        List<Expense> expenses = getAllByDate(id, date);

        return expenses.stream().mapToDouble(Expense::getValue).sum();


    }

    private Expense createExpenseFromAddExpenseReq(AddExpenseReq addExpenseReq) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();

        BusinessWorker businessWorker = businessWorkerService.getByBusinessIdAndKeycloakId(addExpenseReq.getBusinessId(), keycloakId);
        Supplier supplier = supplierService.getByIdAndName(addExpenseReq.getBusinessId(), addExpenseReq.getSupplierName());
        Expense expense = new Expense();

        expense.setWorker(businessWorker);
        expense.setSupplier(supplier);

        expense.setRefTime(addExpenseReq.getRefTime());
        expense.setPaymentMethod(PaymentMethod.fromValue(addExpenseReq.getPaymentMethod()));
        expense.setValue(addExpenseReq.getValue());
        expense.setPaid(addExpenseReq.isPaid());
        expense.setBuyTime(LocalDateTime.now());

        return expense;
    }

    public Boolean delete(Long id) {
        if(!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Non esiste una spesa con questo id: "+id);
        }
        expenseRepository.deleteById(id);
        return true;
    }

    public Page<ExpenseDTO> getPeriodExpenses(Long id, int page, int size, DateRange dateRange) {
        List<Expense> expenses = expenseRepository.findByWorker_Id_Business_IdAndRefTimeBetweenOrderByRefTimeDesc(id, dateRange.getStart(), dateRange.getEnd());
        List<ExpenseDTO> expenseDTOS = expenseMapperService.mapListExpensesToListExpensesDTO(expenses);

        Pageable pageable = PageRequest.of(page, size);

        return PageUtility.convertListToPage(expenseDTOS, pageable);
    }
}
