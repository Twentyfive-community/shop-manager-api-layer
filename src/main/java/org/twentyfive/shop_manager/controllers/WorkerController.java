package org.twentyfive.shop_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager.models.Worker;
import org.twentyfive.shop_manager.services.WorkerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    //TODO togliere pathVariable e prenderselo direttamente dal token
    @GetMapping("/getAllWorkersByBusinessId/{id}")
    public ResponseEntity<List<Worker>> getAllWorkersByBusinessId(@PathVariable Long id) {
        return ResponseEntity.ok().body(workerService.getAllWorkersByBusinessId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addWorker(@RequestBody AddWorkerReq addWorkerReq) {
        return ResponseEntity.ok().body(workerService.addWorker(addWorkerReq));
    }

    @PostMapping("/addInExistentBusiness")
    public ResponseEntity<Boolean> addInExistentBusiness(@RequestBody AddInExistentBusinessReq addInExistentBusinessReq) {
        return ResponseEntity.ok().body(workerService.AddInExistentBusiness(addInExistentBusinessReq));
    }
}
