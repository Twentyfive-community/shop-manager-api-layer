package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.KeycloakClient;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import twentyfive.twentyfiveadapter.dto.keycloakDto.LoginRequest;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.MsUser;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.LoginMsUserRequest;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.ResetPasswordMsUserReq;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final KeycloakClient keycloakClient;
    private final MsUserClient msUserClient;

    @Value("${app.name}")
    private String appName;


    public String login(LoginRequest loginRequest) {

        LoginMsUserRequest loginMsUserRequest = new LoginMsUserRequest();
        loginMsUserRequest.setUsername(loginRequest.getUsername());
        loginMsUserRequest.setPassword(loginRequest.getPassword());
        loginMsUserRequest.setAppName(appName);

        return keycloakClient.login(loginMsUserRequest);
    }

    public Boolean resetPasswordFromEmail(String email) {

        ResetPasswordMsUserReq resetPasswordMsUserReq = new ResetPasswordMsUserReq();
        resetPasswordMsUserReq.setEmail(email);
        resetPasswordMsUserReq.setAppName(appName);

        return msUserClient.resetPasswordFromEmail(resetPasswordMsUserReq);
    }
}
