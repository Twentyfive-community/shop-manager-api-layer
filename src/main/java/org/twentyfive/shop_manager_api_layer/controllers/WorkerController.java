package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager_api_layer.services.WorkerService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<List<SimpleWorker>> getAllByBusinessId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(workerService.getAllByBusinessId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddWorkerReq addWorkerReq) {
        return ResponseEntity.ok().body(workerService.add(addWorkerReq));
    }

    @PostMapping("/addInExistentBusiness")
    public ResponseEntity<Boolean> addInExistentBusiness(@RequestBody AddInExistentBusinessReq addInExistentBusinessReq) {
        return ResponseEntity.ok().body(workerService.AddInExistentBusiness(addInExistentBusinessReq));
    }
}
