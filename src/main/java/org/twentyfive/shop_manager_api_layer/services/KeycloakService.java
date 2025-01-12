package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.KeycloakClient;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.utilities.statics.KeycloakUtility;
import twentyfive.twentyfiveadapter.dto.keycloakDto.KeycloakRole;
import twentyfive.twentyfiveadapter.dto.keycloakDto.KeycloakUser;
import twentyfive.twentyfiveadapter.dto.keycloakDto.TokenRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.username}")
    private String username;
    @Value("${keycloak.password}")
    private String password;
    @Value("${keycloak.granttype}")
    private String grantType;

    private final KeycloakClient keycloakClient;

    public String login(TokenRequest tokenRequest) {
        LinkedHashMap<String, String> details = keycloakClient.getToken(tokenRequest);
        return details.get("access_token");
    }

    private String getAdminBearerToken() {
        TokenRequest tokenRequest = new TokenRequest(clientId, clientSecret, grantType, username, password);
        return "Bearer " + login(tokenRequest);
    }

    public void addEmployeeToRealm(Worker worker){
        String bearerToken = getAdminBearerToken();
        KeycloakUser keycloakUser = KeycloakUtility.addEmployeeToRealm(worker);
        ResponseEntity<Object> response = keycloakClient.add(bearerToken, keycloakUser);
        String keycloakId = KeycloakUtility.getKeycloakIdFromResponse(response);
        addRoleToUser(bearerToken,keycloakId,worker);
        worker.setKeycloakId(keycloakId);
    }

    private void addRoleToUser(String bearerToken, String keycloakId, Worker worker) {
        List<LinkedHashMap<String, String>> rawRoles = keycloakClient.getRoles(bearerToken);
        List<RoleRepresentation> keycloakRoles = rawRoles.stream()
                .map(KeycloakUtility::convertToRoleRepresentation)
                .filter(role -> role.getName().equals(worker.getRole())) // Filtra il ruolo specifico
                .toList();
        keycloakClient.addRoleToUser(bearerToken, keycloakId, keycloakRoles);
    }
}
