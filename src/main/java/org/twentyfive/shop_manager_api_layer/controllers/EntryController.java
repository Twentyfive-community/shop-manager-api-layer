package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddEntryReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetAllTotalEntriesReq;
import org.twentyfive.shop_manager_api_layer.services.EntryService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleGenericEntry;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;

    @GetMapping("/get-all")
    public ResponseEntity<List<SimpleGenericEntry>> getAll() {
        return ResponseEntity.ok().body(entryService.getAll());

    }
    @GetMapping("/get-all-total-entries")
    public ResponseEntity<List<GetAllTotalEntriesReq>> getAllTotalEntries(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(entryService.getAllTotalEntries(authorization));
    }
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddEntryReq addEntryReq) {
        return ResponseEntity.ok().body(entryService.add(addEntryReq));
    }
}
