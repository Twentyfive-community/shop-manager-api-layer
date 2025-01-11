package org.twentyfive.shop_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager.models.Business;
import org.twentyfive.shop_manager.services.BusinessService;


@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody Business business) {
        return ResponseEntity.ok().body(businessService.add(business));
    }
}
