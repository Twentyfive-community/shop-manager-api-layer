package org.twentyfive.shop_manager_api_layer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetRoleRes;

@FeignClient(name = "MsRoleController", url = "${ms-user-business.url}/ms-role")
public interface MsRoleClient {
    @GetMapping("/get-all")
    GetRoleRes getAllRoles(@RequestHeader("authorization") String authorization);
}
