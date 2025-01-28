package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager_api_layer.services.KeycloakService;
import org.twentyfive.shop_manager_api_layer.services.WorkerService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    @PreAuthorize("hasRole('ROLE_super-boss')")
    @GetMapping("/getAllByBusinessId/{id}")
    public ResponseEntity<Page<SimpleWorker>> getAllByBusinessId(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size){
        return ResponseEntity.ok().body(workerService.getAllByBusinessId(id,page, size));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SimpleWorker> getInfoFromToken(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok().body(workerService.getSimpleWorkerFromToken(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddWorkerReq addWorkerReq) {
        return ResponseEntity.ok().body(workerService.add(addWorkerReq));
    }

    @PostMapping("/addInExistentBusiness")
    public ResponseEntity<Boolean> addInExistentBusiness(@RequestBody AddInExistentBusinessReq addInExistentBusinessReq) {
        return ResponseEntity.ok().body(workerService.AddInExistentBusiness(addInExistentBusinessReq));
    }
    @GetMapping("/getRoles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok().body(workerService.getRoles());
    }

}
