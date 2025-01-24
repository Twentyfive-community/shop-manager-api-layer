package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessWorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.ExpenseNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.SupplierNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.repositories.ExpenseRepository;
import org.twentyfive.shop_manager_api_layer.repositories.SupplierRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final SupplierRepository supplierRepository;
    private final ExpenseRepository expenseRepository;
    private final BusinessWorkerRepository businessWorkerRepository;

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

        BusinessWorker businessWorker = businessWorkerRepository.findById_Business_IdAndId_Worker_KeycloakId(addExpenseReq.getBusinessId(), keycloakId).
                orElseThrow(() -> new BusinessWorkerNotFoundException("KeycloakId " +keycloakId+ " non associato a questo business id: " +addExpenseReq.getBusinessId()));
        Supplier supplier = supplierRepository.findByNameAndWorkFor_Id(addExpenseReq.getSupplierName(), addExpenseReq.getBusinessId()).
                orElseThrow(() -> new SupplierNotFoundException("Non esiste questo fornitore " +addExpenseReq.getSupplierName()+" associato a questo businessId: " +addExpenseReq.getBusinessId()));
        Expense expense = new Expense();

        expense.setWorker(businessWorker);
        expense.setSupplier(supplier);

        expense.setPaymentMethod(PaymentMethod.fromValue(addExpenseReq.getPaymentMethod()));
        expense.setValue(addExpenseReq.getValue());

        expense.setBuyTime(LocalDateTime.now());

        return expense;
    }
}
