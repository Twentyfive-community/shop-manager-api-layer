package org.twentyfive.shop_manager_api_layer.utilities.classes.simples;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleWorker {

    private String firstName;
    private String lastName;
    private String fullName;

    private boolean enabled;
    private String role;

    private String email;
    private String phoneNumber;

}
