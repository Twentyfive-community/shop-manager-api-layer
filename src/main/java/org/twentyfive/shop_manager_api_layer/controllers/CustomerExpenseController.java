package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.services.CustomerExpenseService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CustomerExpenseDTO;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer-expense")
public class CustomerExpenseController {

    private final CustomerExpenseService customerExpenseService;

    @PostMapping("/getPeriodExpenses/{id}")
    public ResponseEntity<Page<CustomerExpenseDTO>> getPeriodExpenses(@PathVariable("id") Long id,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "25") int size,
                                                                      @RequestBody DateRange dateRange){
        return ResponseEntity.ok().body(customerExpenseService.getPeriodExpenses(id,page,size,dateRange));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCustomerExpenseReq addCustomerExpenseReq) throws IOException {
        return ResponseEntity.ok().body(customerExpenseService.add(addCustomerExpenseReq));
    }
}
