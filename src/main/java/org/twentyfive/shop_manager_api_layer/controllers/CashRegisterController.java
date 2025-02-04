package org.twentyfive.shop_manager_api_layer.controllers;

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

    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<CashRegister>> getAllByBusinessId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cashRegisterService.getAllByBusinessId(id));
    }
    @GetMapping("/getDetailsById/{id}")
    public ResponseEntity<CashRegisterDetails> getDetailsById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(cashRegisterService.getDetailsById(id));
    }
    @PostMapping("/getByDateAndTimeSlot/{id}")
    public ResponseEntity<CashRegisterDTO> getByDateAndTimeSlot(@PathVariable("id") Long id, @RequestBody GetByDateAndTimeSlotReq request){
        return ResponseEntity.ok().body(cashRegisterService.getByDateAndTimeSlot(id,request));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddCashRegisterReq addCashRegisterReq) throws IOException {
        return ResponseEntity.ok().body(cashRegisterService.add(addCashRegisterReq));
    }

    @PostMapping("/getPeriodStat/{id}")
    public ResponseEntity<GetPeriodStatRes> getPeriodStat(@PathVariable("id") Long id,
                                                          @RequestBody DateRange dateRange){
        return ResponseEntity.ok().body(cashRegisterService.getPeriodStat(id,dateRange));
    }

    @PostMapping("/getPeriodDailyActivities/{id}")
    public ResponseEntity<Page<DailyActivities>> getPeriodDailyActivities(@PathVariable("id") Long id,
                                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                                          @RequestParam(value = "size", defaultValue = "25") int size,
                                                                          @RequestBody DateRange dateRange) {
        return ResponseEntity.ok().body(cashRegisterService.getPeriodDailyActivities(id,page,size,dateRange));
    }

    @PostMapping("/getPeriodClosure/{id}")
    public ResponseEntity<PeriodClosure> getPeriodClosure(@PathVariable("id") Long id,
                                                          @RequestBody DateRange dateRange) {
        return ResponseEntity.ok().body(cashRegisterService.getPeriodClosure(id,dateRange));
    }


}
