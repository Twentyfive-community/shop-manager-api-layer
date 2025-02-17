package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteCustomerRes;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteSupplierRes;
import org.twentyfive.shop_manager_api_layer.services.CustomerService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleCustomer;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getAll/{id}")
    public ResponseEntity<Page<SimpleCustomer>> getAll(@PathVariable Long id,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "25") int size,
                                                       @RequestParam(value = "name", defaultValue = "") String name) {
        return ResponseEntity.ok().body(customerService.getAll(id, page, size, name));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Boolean> add(@PathVariable("id") Long id, @RequestBody AddCustomerReq addCustomerReq) {
        return ResponseEntity.ok().body(customerService.add(id, addCustomerReq));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteByCompanyName(@PathVariable("id") Long id,
                                                       @RequestParam("name") String name) {
        return ResponseEntity.ok().body(customerService.deleteByCompanyName(id, name));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<GetAutoCompleteCustomerRes>> search(@PathVariable("id") Long id,
                                                                   @RequestParam(value = "value", defaultValue = "") String value) {
        return ResponseEntity.ok().body(customerService.search(id, value));
    }
}
