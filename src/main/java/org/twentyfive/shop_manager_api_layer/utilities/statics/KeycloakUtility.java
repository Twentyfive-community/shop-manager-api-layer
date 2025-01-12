package org.twentyfive.shop_manager_api_layer.utilities.statics;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.ResponseEntity;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import twentyfive.twentyfiveadapter.dto.keycloakDto.KeycloakUser;

import java.util.*;

public class KeycloakUtility {
    public static KeycloakUser addEmployeeToRealm(Worker worker) {
        KeycloakUser user = new KeycloakUser();
        user.setEmail(worker.getEmail());
        user.setFirstName(worker.getName());
        user.setLastName(worker.getLastName());
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber", Collections.singletonList(worker.getPhoneNumber()));
        user.setAttributes(attributes);
        user.setEnabled(true);
        return user;
    }

    public static String getKeycloakIdFromResponse(ResponseEntity<Object> response) {
        String[] stringArray = response.getHeaders().get("location").get(0).split("/");
        return stringArray[stringArray.length - 1];
    }

    public static RoleRepresentation convertToRoleRepresentation(LinkedHashMap<String, String> rawRole) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(rawRole.get("name"));
        role.setId(rawRole.get("id"));
        return role;
    }

    /*public static KeycloakUser updateUserForKeycloak(Worker worker) {
        KeycloakUser user = new KeycloakUser();
        user.setFirstName(customerAPA.getFirstName());
        user.setLastName(customerAPA.getLastName());
        user.setEnabled(customerAPA.isEnabled());
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber", Collections.singletonList(customerAPA.getPhoneNumber()));
        attributes.put("note", Collections.singletonList(customerAPA.getNote()));
        user.setAttributes(attributes);
        return user;

    }

     */


}
