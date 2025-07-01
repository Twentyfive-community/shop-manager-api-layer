package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.BusinessUserClient;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.ExpenseMapperService;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.ExpenseRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.BusinessUser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseMapperService expenseMapperService;
    private final SupplierService supplierService;
    private final BusinessUserClient businessUserClient;
    private final MsUserClient msUserClient;

    public Boolean add(AddExpenseReq addExpenseReq, String authorization) throws IOException {
        Expense expense = createExpenseFromAddExpenseReq(addExpenseReq, authorization);
        return expenseRepository.save(expense) != null;
    }

    public boolean update(UpdateExpenseReq updateExpenseReq, String authorization) throws IOException {
        expenseRepository.findById(updateExpenseReq.getId()).orElseThrow(() -> new ExpenseNotFoundException("Non esiste una spesa con questo id! : " + updateExpenseReq.getId()));

        Expense expense = createExpenseFromAddExpenseReq(updateExpenseReq.getAddExpenseReq(), authorization);
        expense.setId(updateExpenseReq.getId());

        return expenseRepository.save(expense) != null;
    }

    public List<Expense> getAllByDate(Long id, LocalDate date) {
        return expenseRepository.findByWorker_Business_IdAndRefTime(id, date);
    }

    public double getTotalExpensesInDateRange(Long id, DateRange dateRange) {
        double totalCost = 0.0;
        LocalDate dateRef = dateRange.getStart();
        while (!dateRef.isAfter(dateRange.getEnd())) {
            totalCost += getTotalExpensesByDate(id, dateRef);
            dateRef = dateRef.plusDays(1);
        }
        return totalCost;
    }

    public double getTotalExpensesByDate(Long id, LocalDate date) {
        List<Expense> expenses = getAllByDate(id, date);

        return expenses.stream().mapToDouble(Expense::getValue).sum();


    }

    private Expense createExpenseFromAddExpenseReq(AddExpenseReq addExpenseReq, String authorization) throws IOException {

        BusinessUser businessUser = businessUserClient.getBusinessUserFromToken(authorization);
        Supplier supplier = supplierService.getByIdAndName(businessUser.getBusiness().getId(), addExpenseReq.getSupplierName());
        Expense expense = new Expense();

        expense.setWorker(businessUser);
        expense.setSupplier(supplier);

        expense.setRefTime(addExpenseReq.getRefTime());
        expense.setPaymentMethod(PaymentMethod.fromValue(addExpenseReq.getPaymentMethod()));
        expense.setValue(addExpenseReq.getValue());
        expense.setPaid(addExpenseReq.isPaid());
        expense.setBuyTime(LocalDateTime.now());

        return expense;
    }

    public Boolean delete(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Non esiste una spesa con questo id: " + id);
        }
        expenseRepository.deleteById(id);
        return true;
    }

    public Page<ExpenseDTO> getPeriodExpenses(String authorization, int page, int size, DateRange dateRange, String payed, String name) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);

        List<Expense> expenses = List.of();
        if (Objects.equals(payed, "Tutte")) {
            expenses = expenseRepository.findByWorker_Business_IdAndRefTimeBetweenAndSupplier_NameContainsIgnoreCaseOrderByRefTimeDesc(business.getId(), dateRange.getStart(), dateRange.getEnd(), name);
        } else if (Objects.equals(payed, "Pagate")) {
            expenses = expenseRepository.findByWorker_Business_IdAndRefTimeBetweenAndPaidAndSupplier_NameContainsIgnoreCaseOrderByRefTimeDesc(business.getId(), dateRange.getStart(), dateRange.getEnd(), true, name);
        } else if (Objects.equals(payed, "Non pagate")) {
            expenses = expenseRepository.findByWorker_Business_IdAndRefTimeBetweenAndPaidAndSupplier_NameContainsIgnoreCaseOrderByRefTimeDesc(business.getId(), dateRange.getStart(), dateRange.getEnd(), false,name);
        }

        List<ExpenseDTO> expenseDTOS = expenseMapperService.mapListExpensesToListExpensesDTO(expenses);
        Pageable pageable = PageRequest.of(page, size);
        return PageUtility.convertListToPage(expenseDTOS, pageable);
    }
}
