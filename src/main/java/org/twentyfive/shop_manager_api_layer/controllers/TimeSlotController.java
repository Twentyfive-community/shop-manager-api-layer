package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;
import org.twentyfive.shop_manager_api_layer.services.TimeSlotService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeslot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<TimeSlot>> getAllByBusinessId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(timeSlotService.getAllByBusinessId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddTimeSlotReq addTimeSlotReq) {
        return ResponseEntity.ok().body(timeSlotService.add(addTimeSlotReq));
    }
}
