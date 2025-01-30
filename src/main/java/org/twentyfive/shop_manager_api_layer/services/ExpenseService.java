package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessWorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.ExpenseMapperService;
import org.twentyfive.shop_manager_api_layer.models.BusinessSupplier;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessSupplierRepository;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.repositories.ExpenseRepository;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
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
public class ExpenseService {

    private final SupplierRepository supplierRepository;
    private final BusinessSupplierRepository businessSupplierRepository;
    private final ExpenseRepository expenseRepository;
    private final BusinessWorkerRepository businessWorkerRepository;

    private final ExpenseMapperService expenseMapperService;

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

    private Expense createExpenseFromAddExpenseReq(AddExpenseReq addExpenseReq) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();

        BusinessWorker businessWorker = businessWorkerRepository.findById_Business_IdAndId_Worker_KeycloakIdAndDisabledFalse(addExpenseReq.getBusinessId(), keycloakId).
                orElseThrow(() -> new BusinessWorkerNotFoundException("KeycloakId " +keycloakId+ " disabilitato per questo business id: " +addExpenseReq.getBusinessId()));
        BusinessSupplier businessSupplier = businessSupplierRepository.findById_Business_IdAndId_Supplier_Name(addExpenseReq.getBusinessId(),addExpenseReq.getSupplierName()).
                orElseThrow(() -> new SupplierNotFoundException("Non esiste questo fornitore " +addExpenseReq.getSupplierName()+" associato a questo businessId: " +addExpenseReq.getBusinessId()));
        Expense expense = new Expense();

        expense.setWorker(businessWorker);
        expense.setSupplier(businessSupplier.getId().getSupplier());

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
