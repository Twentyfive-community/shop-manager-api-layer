package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWorkerReq {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
