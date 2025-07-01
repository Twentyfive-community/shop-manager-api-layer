package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.services.ExpenseService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.ExpenseDTO;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/get-period-expenses")
    public ResponseEntity<Page<ExpenseDTO>> getPeriodExpenses(HttpServletRequest request,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "payed", defaultValue = "Tutte") String payed,
                                                              @RequestParam(value = "size", defaultValue = "25") int size,
                                                              @RequestBody DateRange dateRange) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(expenseService.getPeriodExpenses(authorization, page, size, dateRange, payed));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddExpenseReq addExpenseReq,
                                       HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(expenseService.add(addExpenseReq, authorization));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateExpenseReq updateExpenseReq,
                                          HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(expenseService.update(updateExpenseReq, authorization));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(expenseService.delete(id));
    }
}
