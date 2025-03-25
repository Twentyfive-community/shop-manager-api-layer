package org.twentyfive.shop_manager_api_layer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.LoginMsUserRequest;

import java.io.IOException;

@FeignClient(name = "KeycloakController", url = "${ms-user-business.url}/keycloak")
public interface KeycloakClient {

    @PostMapping("/login")
    String login(@RequestBody LoginMsUserRequest loginRequest);
}
