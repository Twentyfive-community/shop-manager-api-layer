package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddEntryReq;
import org.twentyfive.shop_manager_api_layer.services.EntryService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleGenericEntry;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<SimpleGenericEntry>> getAll() {
        return ResponseEntity.ok().body(entryService.getAll());

    }
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddEntryReq addEntryReq) {
        return ResponseEntity.ok().body(entryService.add(addEntryReq));
    }
}
