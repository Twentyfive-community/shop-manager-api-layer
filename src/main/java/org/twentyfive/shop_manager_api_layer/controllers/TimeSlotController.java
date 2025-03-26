package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.services.TimeSlotService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CheckCashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeslot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<SimpleTimeSlot>> getAllByBusinessId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(timeSlotService.getAllByBusinessId(id));
    }

    @GetMapping("/checkCashRegisterInTimeSlot/{id}")
    public ResponseEntity<List<CheckCashRegister>> checkCashRegisterInTimeSlot(@PathVariable("id") Long businessId,
                                                                               @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok().body(timeSlotService.checkCashRegisterInTimeSlot(businessId,date));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddTimeSlotReq addTimeSlotReq, HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(timeSlotService.add(addTimeSlotReq,authorization));
    }
}
