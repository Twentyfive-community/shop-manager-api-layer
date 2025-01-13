package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCashRegisterReq;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.services.CashRegisterService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cash-register")
public class CashRegisterController {
    private final CashRegisterService cashRegisterService;

    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<CashRegister>> getAllByBusinessId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cashRegisterService.getAllByBusinessId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCashRegisterReq addCashRegisterReq) throws IOException {
        return ResponseEntity.ok().body(cashRegisterService.add(addCashRegisterReq));
    }
}
