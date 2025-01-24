package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAllSupplierNameRes;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping("/addList/{id}")
    public ResponseEntity<Boolean> addList(@RequestParam("id")Long id,@RequestBody List<AddSupplierReq> addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.addList(id,addSupplierReq));
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Boolean> add(@RequestParam("id")Long id, @RequestBody AddSupplierReq addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.add(id,addSupplierReq));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<GetAllSupplierNameRes> search(@RequestParam("id")Long id, @RequestParam(value ="value",defaultValue = "") String value){
        return ResponseEntity.ok().body(supplierService.search(id,value));
    }
}
