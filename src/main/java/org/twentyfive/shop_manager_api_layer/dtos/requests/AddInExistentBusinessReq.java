package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddInExistentBusinessReq {
    private Long businessId;
    private Long workerId;
    private String role;
}
