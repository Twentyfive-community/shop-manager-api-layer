package org.twentyfive.shop_manager.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager.models.Worker;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddWorkerReq {
    private Long businessId;
    private Worker worker;
}
