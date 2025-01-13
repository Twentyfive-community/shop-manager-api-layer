package org.twentyfive.shop_manager_api_layer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.KeycloakClient;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.KeycloakUtility;
import twentyfive.twentyfiveadapter.dto.keycloakDto.KeycloakUser;
import twentyfive.twentyfiveadapter.dto.keycloakDto.TokenRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public String getAccessToken(String clientId, String clientSecret, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        TokenRequest request = new TokenRequest(clientId, clientSecret, "password", username, password);
        System.out.println(request);
        ResponseEntity<Object> response = keycloakClient.getToken(request);
        System.out.println(response.getBody());
        System.out.println("IL CACCHIO DEL TOKEN");
        System.out.println(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        Map responseMap = objectMapper.convertValue(response.getBody(), Map.class);
        return (String) responseMap.get("access_token");
    }

    private String getAdminBearerToken() {
        return "Bearer " + getAccessToken(clientId, clientSecret, username, password);
    }

    public SimpleWorker getUserByKeycloakId() throws IOException {
        String idKeycloak = JwtUtility.getIdKeycloak();
        return keycloakClient.getUserById(getAdminBearerToken(), idKeycloak);
    }

    public void addEmployeeToRealm(Worker worker, String role){
        String bearerToken = getAdminBearerToken();
        KeycloakUser keycloakUser = KeycloakUtility.addEmployeeToRealm(worker);
        ResponseEntity<Object> response = keycloakClient.add(bearerToken, keycloakUser);
        String keycloakId = KeycloakUtility.getKeycloakIdFromResponse(response);
        addRoleToUser(bearerToken,keycloakId,role);
        worker.setKeycloakId(keycloakId);
    }

    private void addRoleToUser(String bearerToken, String keycloakId,String role) {
        List<LinkedHashMap<String, String>> rawRoles = keycloakClient.getRoles(bearerToken);
        List<RoleRepresentation> keycloakRoles = rawRoles.stream()
                .map(KeycloakUtility::convertToRoleRepresentation)
                .filter(keycloakRole -> keycloakRole.getName().equals(role)) // Filtra il ruolo specifico
                .toList();
        keycloakClient.addRoleToUser(bearerToken, keycloakId, keycloakRoles);
    }
}
