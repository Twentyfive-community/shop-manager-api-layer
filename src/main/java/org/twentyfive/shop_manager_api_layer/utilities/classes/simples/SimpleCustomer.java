package org.twentyfive.shop_manager_api_layer.utilities.classes.simples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCustomer {
    private Long id;

    private String companyName;
    private String registeredOffice;
    private String vatNumber;
    private String pec;
    private String email;
    private String sdi;
}
