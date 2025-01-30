package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.ChangeRoleReq;
import org.twentyfive.shop_manager_api_layer.services.BusinessWorkerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business-worker")
public class BusinessWorkerController {
    private final BusinessWorkerService businessWorkerService;

    @PatchMapping("/toggleStatus/{id}")
    public ResponseEntity<Boolean> toggleStatus(@PathVariable("id") Long id,@RequestParam("email") String email){
        return ResponseEntity.ok().body(businessWorkerService.toggleStatus(id,email));
    }
    @PatchMapping("/changeRole/{id}")
    public ResponseEntity<Boolean> changeRole(@PathVariable("id") Long id, @RequestBody ChangeRoleReq changeRoleReq){
        return ResponseEntity.ok().body(businessWorkerService.changeRole(id,changeRoleReq));
    }
}
