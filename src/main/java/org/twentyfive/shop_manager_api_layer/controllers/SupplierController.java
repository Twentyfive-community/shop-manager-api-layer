package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<String>> getAllByBusinessId(@PathVariable Long id) {
        return ResponseEntity.ok().body(supplierService.getAllByBusinessId(id));
    }
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddSupplierReq addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.add(addSupplierReq));
    }
}
