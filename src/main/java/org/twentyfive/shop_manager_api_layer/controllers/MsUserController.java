package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.AddMsUserReq;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.UpdateUserReq;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetUserRes;

import java.io.IOException;

@RestController
@RequestMapping("/ms-user")
@RequiredArgsConstructor
public class MsUserController {
    private final MsUserClient msUserClient;

    @PostMapping("/get-all")
    public ResponseEntity<Page<GetUserRes>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size,
            HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok().body(msUserClient.getAllUser(authorization,page, size));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addUser(HttpServletRequest request,
                                           @RequestBody AddMsUserReq msUser) {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok(msUserClient.addUser(authorization,msUser));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(HttpServletRequest request,
                                              @RequestBody UpdateUserReq updateUserReq) throws IOException {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok(msUserClient.updateUser(authorization,updateUserReq));
    }
}
