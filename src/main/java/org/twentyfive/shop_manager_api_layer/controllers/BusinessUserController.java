package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.clients.BusinessUserClient;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.AddExistingUserReq;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.ChangeRoleReq;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetInfoMsUserRes;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business-worker")
public class BusinessUserController {
    private final BusinessUserClient businessUserClient;

    @PatchMapping("/toggle-status")
    public ResponseEntity<Boolean> toggleStatus(@RequestParam("email") String email,
                                                HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        return ResponseEntity.ok().body(businessUserClient.changeStatus(authorization,email));
    }

    @PatchMapping("/change-role")
    public ResponseEntity<Boolean> changeRole(@RequestBody ChangeRoleReq changeRoleReq,
                                              HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        return ResponseEntity.ok().body(businessUserClient.changeRole(authorization,changeRoleReq));
    }

    @GetMapping("/info")
    public ResponseEntity<GetInfoMsUserRes> info(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");

        return ResponseEntity.ok(businessUserClient.info(authorization));
    }

    @PostMapping("/add-existing-user")
    public ResponseEntity<Boolean> addExistingUser(HttpServletRequest request,
                                                   @RequestBody AddExistingUserReq req) {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok(businessUserClient.addExistingUser(authorization,req));
    }

    @GetMapping("/get-all-businesses-associated")
    public ResponseEntity<List<String>> getAllBusinessesAssociated(HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok(businessUserClient.getAllBusinessesAssociated(authorization));
    }
}
