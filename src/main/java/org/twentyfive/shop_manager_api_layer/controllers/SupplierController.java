package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SuppliersAndPaymentMethods;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/getAllSuppliersAndPaymentMethod/{id}")
    public ResponseEntity<SuppliersAndPaymentMethods> getAllSuppliersAndPaymentMethod(@PathVariable Long id) {
        return ResponseEntity.ok().body(supplierService.getAllSuppliersAndPaymentMethod(id));
    }
    @PostMapping("/addList/{id}")
    public ResponseEntity<Boolean> addList(@RequestParam("id")Long id,@RequestBody List<AddSupplierReq> addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.addList(id,addSupplierReq));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Boolean> add(@RequestParam("id")Long id, @RequestBody AddSupplierReq addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.add(id,addSupplierReq));
    }
}
