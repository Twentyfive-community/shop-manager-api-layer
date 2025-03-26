package org.twentyfive.shop_manager_api_layer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.BusinessUser;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.AddExistingUserReq;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.ChangeRoleReq;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetInfoMsUserRes;

import java.io.IOException;

@FeignClient(name = "BusinessUserController", url = "${ms-user-business.url}/business-user")
public interface BusinessUserClient {

    @GetMapping("/changeStatus")
    Boolean changeStatus(@RequestHeader("authorization") String authorization,
                         @RequestParam String email);

    @PutMapping("/change-role")
    Boolean changeRole(@RequestHeader("authorization") String authorization,
                       @RequestBody ChangeRoleReq req);

    @GetMapping("/info")
    GetInfoMsUserRes info(@RequestHeader("authorization") String authorization) throws IOException;

    @PostMapping("/add-existing-user")
    Boolean addExistingUser(@RequestHeader("authorization") String authorization,
                            @RequestBody AddExistingUserReq req);

    @GetMapping("/get-from-token")
    BusinessUser getBusinessUserFromToken(@RequestHeader("authorization") String authorization) throws IOException;
}
