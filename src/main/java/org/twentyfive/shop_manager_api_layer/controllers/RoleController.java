package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twentyfive.shop_manager_api_layer.clients.MsRoleClient;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetRoleRes;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final MsRoleClient roleClient;

    @GetMapping("/get-all")
    public ResponseEntity<GetRoleRes> getAllRoles(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return ResponseEntity.ok(roleClient.getAllRoles(authorization));
    }
}
