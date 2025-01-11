package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.KeycloakClient;
import twentyfive.twentyfiveadapter.dto.keycloakDto.TokenRequest;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final KeycloakClient keycloakClient;


    public String login(TokenRequest tokenRequest) {
        LinkedHashMap<String, String> details = keycloakClient.getToken(tokenRequest);
        return details.get("access_token");
    }
}
