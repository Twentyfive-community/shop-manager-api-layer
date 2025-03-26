package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateCustomerExpenseReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateExpenseReq;
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
                                                                      @RequestParam(value = "value", defaultValue = "") String value,
                                                                      @RequestBody DateRange dateRange){
        return ResponseEntity.ok().body(customerExpenseService.getPeriodExpenses(id,page,size,dateRange,value));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCustomerExpenseReq addCustomerExpenseReq,
                                       HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");

        return ResponseEntity.ok().body(customerExpenseService.add(addCustomerExpenseReq,authorization));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UpdateCustomerExpenseReq updateCustomerExpenseReq,
                                          HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(customerExpenseService.update(updateCustomerExpenseReq,authorization));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok().body(customerExpenseService.delete(id));
    }
}
