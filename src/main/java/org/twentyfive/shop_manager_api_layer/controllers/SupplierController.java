package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierGroupReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteSupplierRes;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplierGroup;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplier;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/getAll/{id}")
    public ResponseEntity<Page<SimpleSupplier>> getAll(@PathVariable Long id,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "25") int size) {
        return ResponseEntity.ok().body(supplierService.getAll(id,page,size));

    }
    @PostMapping("/addList/{id}")
    public ResponseEntity<Boolean> addList(@PathVariable("id")Long id,@RequestBody List<AddSupplierReq> addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.addList(id,addSupplierReq));
    }

    @GetMapping("/searchGroups/{id}")
    public ResponseEntity<List<String>> searchGroups(@PathVariable("id")Long id, @RequestParam(value ="value",defaultValue = "") String value){
        return ResponseEntity.ok().body(supplierService.searchGroups(id, value));
    }
    @PostMapping("/add/{id}")
    public ResponseEntity<Boolean> add(@PathVariable("id")Long id, @RequestBody AddSupplierReq addSupplierReq) {
        return ResponseEntity.ok().body(supplierService.add(id,addSupplierReq));
    }
    @PostMapping("/addGroup/{id}")
    public ResponseEntity<Boolean> addGroup(@PathVariable("id")Long id, @RequestBody AddSupplierGroupReq addSupplierGroupReq) {
        return ResponseEntity.ok().body(supplierService.addGroup(id,addSupplierGroupReq));
    }
    @PostMapping("/getAllGroups/{id}")
    public ResponseEntity<Page<SimpleSupplierGroup>> getAllGroups(@PathVariable Long id,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "25") int size) {
        return ResponseEntity.ok().body(supplierService.getAllGroups(id,page,size));
    }
    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<Boolean> deleteGroupById(@PathVariable("id")Long id,@RequestParam("name") String name) {
        return ResponseEntity.ok().body(supplierService.deleteGroup(id, name));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id")Long id, @RequestBody UpdateSupplierReq updateSupplierReq) {
        return ResponseEntity.ok().body(supplierService.update(id,updateSupplierReq));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteByName(@PathVariable("id")Long id, @RequestParam("name") String name) {
        return ResponseEntity.ok().body(supplierService.deleteByName(id,name));
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<GetAutoCompleteSupplierRes>> search(@PathVariable("id")Long id, @RequestParam(value ="value",defaultValue = "") String value){
        return ResponseEntity.ok().body(supplierService.search(id, value));
    }
}
