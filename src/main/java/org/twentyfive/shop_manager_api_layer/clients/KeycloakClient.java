package org.twentyfive.shop_manager_api_layer.clients;

import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;
import twentyfive.twentyfiveadapter.dto.keycloakDto.KeycloakRole;
import twentyfive.twentyfiveadapter.dto.keycloakDto.PasswordUpdateKeycloak;
import twentyfive.twentyfiveadapter.dto.keycloakDto.TokenRequest;

import java.util.LinkedHashMap;
import java.util.List;

@FeignClient(name = "WorkerController", url = "http://80.211.123.141:8080")
public interface KeycloakClient {

    @RequestMapping(method = RequestMethod.POST, value="/realms/${keycloak.realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Object> getToken(@RequestBody TokenRequest params);

    @RequestMapping(method = RequestMethod.GET, value = "/admin/realms/${keycloak.realm}/users", produces = "application/json")
    ResponseEntity<List<UserRepresentation>> getAllUsers(@RequestHeader("Authorization") String accessToken);

    @RequestMapping(method = RequestMethod.GET, value = "/admin/realms/${keycloak.realm}/users", produces = "application/json")
    ResponseEntity<List<UserRepresentation>> getUserFromEmail(@RequestHeader("Authorization") String accessToken, @RequestParam String email);
    @GetMapping("/admin/realms/${keycloak.realm/users/{id}")
    SimpleWorker getUserById(@RequestHeader("Authorization") String accessToken, @PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value="/admin/realms/${keycloak.realm}/ui-ext/brute-force-user")
    ResponseEntity<List<UserRepresentation>> search(@RequestHeader("Authorization") String accessToken, @RequestParam String search);

    @RequestMapping(method = RequestMethod.POST, value = "/admin/realms/${keycloak.realm}/users", produces = "application/json")
    ResponseEntity<Object> add(@RequestHeader("Authorization") String accessToken, @RequestBody UserRepresentation user);
    @RequestMapping(method = RequestMethod.PUT, value ="/admin/realms/${keycloak.realm}/users/{id}")
    ResponseEntity<UserRepresentation> update(@RequestHeader("Authorization") String accessToken, @PathVariable("id") String id,@RequestBody UserRepresentation user);
    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/realms/${keycloak.realm}/users/{id}", produces = "application/json")
    ResponseEntity<Object> delete(@RequestHeader("Authorization") String accessToken, @PathVariable String id);
    @RequestMapping(method = RequestMethod.PUT, value = "/admin/realms/${keycloak.realm}/users/{userId}/execute-actions-email", produces = "application/json")
    ResponseEntity<Object> resetPassword(@RequestHeader("Authorization") String accessToken, @PathVariable("userId") String userId, @RequestBody List<String> actions);
    @RequestMapping(method = RequestMethod.PUT, value = "/admin/realms/${keycloak.realm}/users/{id}/reset-password", produces = "application/json")
    ResponseEntity<Object> updatePassword(@RequestHeader("Authorization") String accessToken, @PathVariable String id, @RequestBody PasswordUpdateKeycloak newPassword);
    @RequestMapping(method = RequestMethod.GET, value ="admin/realms/${keycloak.realm}/roles")
    List<LinkedHashMap<String,String>> getRoles(@RequestHeader("Authorization") String accessToken);

    @RequestMapping(method = RequestMethod.POST, value = "/admin/realms/${keycloak.realm}/users/{id}/role-mappings/realm", produces = "application/json")
    ResponseEntity<Object> addRoleToUser(@RequestHeader("Authorization") String accessToken,
                                         @PathVariable String id,
                                         @RequestBody List<RoleRepresentation> roles);

    @RequestMapping(method = RequestMethod.DELETE, value = "/admin/realms/${keycloak.realm}/users/{id}/role-mappings/clients/{clientIdRole}", produces = "application/json")
    ResponseEntity<Object> deleteRoleToUser(@RequestHeader("Authorization") String accessToken,
                                            @PathVariable String id,
                                            @PathVariable String clientIdRole,
                                            @RequestBody List<RoleRepresentation> roles);
    @RequestMapping(method = RequestMethod.GET, value = "/admin/realms/${keycloak.realm}/users/{id}/role-mappings", produces = "application/json")
    ResponseEntity<MappingsRepresentation> getUserRole(@RequestHeader("Authorization") String accessToken, @PathVariable String id);

}
