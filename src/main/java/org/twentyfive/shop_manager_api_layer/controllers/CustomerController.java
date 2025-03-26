package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCustomerReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteCustomerRes;
import org.twentyfive.shop_manager_api_layer.services.CustomerService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleCustomer;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<SimpleCustomer>> getAll(HttpServletRequest request,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "25") int size,
                                                       @RequestParam(value = "name", defaultValue = "") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(customerService.getAll(authorization, page, size, name));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(HttpServletRequest request, @RequestBody AddCustomerReq addCustomerReq) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(customerService.add(authorization, addCustomerReq));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteByCompanyName(HttpServletRequest request,
                                                       @RequestParam("name") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(customerService.deleteByCompanyName(authorization, name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetAutoCompleteCustomerRes>> search(HttpServletRequest request,
                                                                   @RequestParam(value = "value", defaultValue = "") String value) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(customerService.search(authorization, value));
    }
}
