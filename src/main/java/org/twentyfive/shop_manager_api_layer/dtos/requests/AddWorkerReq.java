package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.Worker;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddWorkerReq {
    private Long businessId;
    private Worker worker;
}
