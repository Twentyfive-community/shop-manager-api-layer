package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierGroupReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetSupplierWithoutGroupReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.UpdateSupplierReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetAutoCompleteSupplierRes;
import org.twentyfive.shop_manager_api_layer.services.SupplierService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplierGroup;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleSupplier;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<SimpleSupplier>> getAll(HttpServletRequest request,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "25") int size,
                                                       @RequestParam(value = "name", defaultValue = "") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.getAll(authorization, page, size, name));
    }

    @PostMapping("/add-list")
    public ResponseEntity<Boolean> addList(@RequestBody List<AddSupplierReq> addSupplierReq,
                                           HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.addList(addSupplierReq,authorization));
    }

    @GetMapping("/search-groups")
    public ResponseEntity<List<String>> searchGroups(HttpServletRequest request, @RequestParam(value = "value", defaultValue = "") String value) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.searchGroups(authorization, value));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody AddSupplierReq addSupplierReq,
                                       HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.add(addSupplierReq,authorization));
    }

    @PostMapping("/add-group")
    public ResponseEntity<Boolean> addGroup(HttpServletRequest request,
                                            @RequestBody AddSupplierGroupReq addSupplierGroupReq) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.addGroup(authorization, addSupplierGroupReq));
    }

    @GetMapping("/get-all-groups")
    public ResponseEntity<Page<SimpleSupplierGroup>> getAllGroups(HttpServletRequest request,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "25") int size,
                                                                  @RequestParam(value ="name", defaultValue = "") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.getAllGroups(authorization, page, size,name));
    }

    @DeleteMapping("/delete-group")
    public ResponseEntity<Boolean> deleteGroupById(HttpServletRequest request, @RequestParam("name") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.deleteGroup(authorization, name));
    }

    @PatchMapping("/update")
    public ResponseEntity<Boolean> update(HttpServletRequest request, @RequestBody UpdateSupplierReq updateSupplierReq) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.update(authorization, updateSupplierReq));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteByName(HttpServletRequest request, @RequestParam("name") String name) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.deleteByName(authorization, name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetAutoCompleteSupplierRes>> search(HttpServletRequest request, @RequestParam(value = "value", defaultValue = "") String value)throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.search(authorization, value));
    }

    @GetMapping("/get-all-names")
    public ResponseEntity<List<String>> getAllNames(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.getAllNames(authorization));
    }

    @GetMapping("/get-supplier-without-group")
    public ResponseEntity<GetSupplierWithoutGroupReq> getSupplierWithoutGroup(HttpServletRequest request,
                                                                              @RequestParam(value = "name", required = false) String name,
                                                                              @RequestParam(value = "value", defaultValue = "") String value) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(supplierService.getSupplierWithoutGroup(authorization,name, value));
    }

}
