package org.twentyfive.shop_manager_api_layer.utilities.statics;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JwtUtility {

    private static JsonNode rootNodeFromToken() throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];

        DecodedJWT decoded = JWT.decode(token);
        String payload = new String(java.util.Base64.getDecoder().decode(decoded.getPayload()));

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(payload);
    }
    public static List<String> getRoles() throws IOException {
        JsonNode rootNode = rootNodeFromToken();
        JsonNode apaAppNode = rootNode.path("realm_access").path("roles");

        List<String> roles = new ArrayList<>();
        if (apaAppNode.isArray()) {
            Iterator<JsonNode> elements = apaAppNode.elements();
            while (elements.hasNext()) {
                roles.add(elements.next().asText());
            }
        }

        return roles;
    }

    public static String getIdKeycloak() throws IOException {
        JsonNode rootNode = rootNodeFromToken();
        JsonNode keycloakNode = rootNode.path("sub");
        return keycloakNode.asText();
    }
}