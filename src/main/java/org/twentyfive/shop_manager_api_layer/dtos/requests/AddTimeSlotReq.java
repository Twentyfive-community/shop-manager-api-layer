package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTimeSlotReq {
    private Long businessId;
    private SimpleTimeSlot timeSlot;
}
