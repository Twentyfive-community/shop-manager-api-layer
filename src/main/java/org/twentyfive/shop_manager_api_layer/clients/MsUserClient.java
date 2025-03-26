package org.twentyfive.shop_manager_api_layer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.MsUser;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.AddMsUserReq;
import twentyfive.twentyfiveadapter.request.msUserBusinessRequests.UpdateUserReq;
import twentyfive.twentyfiveadapter.response.msUserBusinessResponses.GetUserRes;

import java.io.IOException;

@FeignClient(name = "MsUserController", url = "${ms-user-business.url}/ms-user")
public interface MsUserClient {
    @PutMapping("/reset-password")
    Boolean resetPasswordFromEmail(@RequestHeader("authorization") String authorization,
                                   @RequestParam("email") String email) throws IOException;

    @PostMapping("/get-all")
    Page<GetUserRes> getAllUser(@RequestHeader("authorization") String authorization,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "25") int size);

    @PostMapping("/add")
    Boolean addUser(@RequestHeader("authorization") String authorization,
                    @RequestBody AddMsUserReq msUser);

    @PutMapping("/update")
    Boolean updateUser(@RequestHeader("authorization") String authorization,
                       @RequestBody UpdateUserReq request) throws IOException;

    @GetMapping("/get-business-from-token")
    Business getBusinessFromToken(@RequestHeader("authorization") String authorization) throws IOException;

    @GetMapping("/get-user-from-token")
    MsUser getUserFromToken(@RequestHeader("authorization") String authorization) throws IOException;

}
