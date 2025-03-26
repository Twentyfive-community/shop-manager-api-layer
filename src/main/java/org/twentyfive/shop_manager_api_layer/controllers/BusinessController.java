package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAllPaymentMethodRes;
import org.twentyfive.shop_manager_api_layer.services.BusinessService;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping("/get-all-payment-method")
    public ResponseEntity<GetAllPaymentMethodRes> getAllPaymentMethod() {
        return ResponseEntity.ok().body(businessService.getAllPaymentMethod());
    }
}
