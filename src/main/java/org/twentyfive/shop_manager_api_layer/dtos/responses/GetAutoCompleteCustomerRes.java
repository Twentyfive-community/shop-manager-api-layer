package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAutoCompleteCustomerRes {
    private String companyName;
}
