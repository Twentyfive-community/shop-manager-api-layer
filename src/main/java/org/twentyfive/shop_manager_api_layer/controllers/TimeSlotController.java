package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.services.TimeSlotService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CheckCashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeslot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;
    private final MsUserClient msUserClient;

    @GetMapping("/get-all")
    public ResponseEntity<List<SimpleTimeSlot>> getAll(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        Business business = msUserClient.getBusinessFromToken(authorization);
        return ResponseEntity.ok().body(timeSlotService.getAll(business.getId()));
    }

    @GetMapping("/check-cash-register-in-time-slot")
    public ResponseEntity<List<CheckCashRegister>> checkCashRegisterInTimeSlot(HttpServletRequest request,
                                                                               @RequestParam("date") LocalDate date) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(timeSlotService.checkCashRegisterInTimeSlot(authorization,date));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddTimeSlotReq addTimeSlotReq, HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(timeSlotService.add(addTimeSlotReq,authorization));
    }
}
