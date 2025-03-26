package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCashRegisterReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetByDateAndTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetPeriodStatRes;
import org.twentyfive.shop_manager_api_layer.utilities.classes.*;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.services.CashRegisterService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cash-register")
public class CashRegisterController {
    private final CashRegisterService cashRegisterService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CashRegister>> getAll(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(cashRegisterService.getAll(authorization));
    }
    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<CashRegisterDetails> getDetailsById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cashRegisterService.getDetailsById(id));
    }
    @PostMapping("/get-by-date-and-time-slot")
    public ResponseEntity<CashRegisterDTO> getByDateAndTimeSlot(@RequestBody GetByDateAndTimeSlotReq req,
                                                                HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(cashRegisterService.getByDateAndTimeSlot(authorization,req));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCashRegisterReq addCashRegisterReq,
                                       HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(cashRegisterService.add(addCashRegisterReq,authorization));
    }

    @PostMapping("/get-period-daily-activities")
    public ResponseEntity<Page<DailyActivities>> getPeriodDailyActivities(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                          @RequestParam(value = "size", defaultValue = "25") int size,
                                                                          @RequestBody DateRange dateRange,
                                                                          HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(cashRegisterService.getPeriodDailyActivities(authorization,page,size,dateRange));
    }
    @PostMapping("/get-period-stats")
    public ResponseEntity<GetPeriodStatRes> getPeriodStats(HttpServletRequest request,
                                                           @RequestBody DateRange dateRange) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(cashRegisterService.getPeriodStats(authorization,dateRange));
    }

}
