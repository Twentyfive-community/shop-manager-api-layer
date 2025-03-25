package org.twentyfive.shop_manager_api_layer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.services.KeycloakService;
import twentyfive.twentyfiveadapter.dto.keycloakDto.LoginRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak")
public class KeycloakController {
    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return keycloakService.login(loginRequest);
    }

    @PatchMapping("/reset-password/{email}")
    public ResponseEntity<Boolean> resetPasswordFromEmail(@PathVariable("email") String email,
                                                          HttpServletRequest request) throws IOException {
        String authorization = request.getHeader("Authorization");

        return ResponseEntity.ok().body(keycloakService.resetPasswordFromEmail(authorization,email));
    }
}