package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerReq {

    private Long id;
    private String companyName;
    private String registeredOffice;
    private String vatNumber;
    private String pec;
    private String email;
    private String sdi;
}
