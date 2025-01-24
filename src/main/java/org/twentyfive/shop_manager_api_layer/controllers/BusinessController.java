package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAllPaymentMethodRes;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.services.BusinessService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CheckCashRegister;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddBusinessReq addBusinessReq) {
        return ResponseEntity.ok().body(businessService.add(addBusinessReq));
    }

    @GetMapping("/getAllPaymentMethod")
    public ResponseEntity<GetAllPaymentMethodRes> getAllPaymentMethod() {
        return ResponseEntity.ok().body(businessService.getAllPaymentMethod());
    }
}
