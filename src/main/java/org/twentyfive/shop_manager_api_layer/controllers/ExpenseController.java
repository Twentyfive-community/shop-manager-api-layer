package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
import org.twentyfive.shop_manager_api_layer.services.ExpenseService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddExpenseReq addExpenseReq) throws IOException {
        return ResponseEntity.ok().body(expenseService.add(addExpenseReq));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateExpenseReq updateExpenseReq) throws IOException {
        return ResponseEntity.ok().body(expenseService.update(updateExpenseReq));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok().body(expenseService.delete(id));
    }
}
