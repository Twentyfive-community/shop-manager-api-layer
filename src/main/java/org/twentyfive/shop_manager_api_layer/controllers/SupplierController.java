package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SuppliersAndPaymentMethods;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    @GetMapping("/getAllSuppliersAndPaymentMethod/{id}")
    public ResponseEntity<SuppliersAndPaymentMethods> getAllSuppliersAndPaymentMethod(@PathVariable Long id) {
        return ResponseEntity.ok().body(supplierService.getAllSuppliersAndPaymentMethod(id));
    }
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddSupplierReq addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.add(addSupplierReq));
    }
}
