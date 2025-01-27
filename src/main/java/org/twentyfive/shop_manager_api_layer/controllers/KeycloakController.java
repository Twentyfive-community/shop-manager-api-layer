package org.twentyfive.shop_manager_api_layer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.services.KeycloakService;
import twentyfive.twentyfiveadapter.dto.keycloakDto.TokenRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak")
public class KeycloakController {

    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public String login(@RequestBody TokenRequest tokenRequest) {
        return keycloakService.getAccessToken(tokenRequest.getClient_id(), tokenRequest.getClient_secret(), tokenRequest.getUsername(), tokenRequest.getPassword());
    }

    @PatchMapping("/reset-password/{email}")
    public ResponseEntity<Boolean> resetPasswordFromEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(keycloakService.resetPasswordFromEmail(email));
    }
}
