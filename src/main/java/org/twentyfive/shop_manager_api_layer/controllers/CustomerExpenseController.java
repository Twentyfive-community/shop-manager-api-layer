package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.services.CustomerExpenseService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer-expense")
public class CustomerExpenseController {

    private final CustomerExpenseService customerExpenseService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCustomerExpenseReq addCustomerExpenseReq) throws IOException {
        return ResponseEntity.ok().body(customerExpenseService.add(addCustomerExpenseReq));
    }
}
