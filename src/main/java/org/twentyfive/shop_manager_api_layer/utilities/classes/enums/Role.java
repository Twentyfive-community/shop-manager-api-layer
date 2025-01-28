package org.twentyfive.shop_manager_api_layer.utilities.classes.enums;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    SUPER_BOSS("Super capo"),
    BOSS("Capo"),
    EMPLOYEE("Dipendente");

    private final String role;

    Role(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }

    // Metodo per convertire da stringa a enum
    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.role.equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Ruolo sconosciuto: " + role);
    }
    public static String getKeycloakRole(Role role){
        return getKeycloakRole(role.getRole());
    }
    public static String getKeycloakRole(String role){
        switch (role){
            case "Super capo" -> {
                return "super-boss";
            }
            case "Capo" -> {
                return "boss";
            }
            case "Dipendente" -> {
                return "employee";
            }
            default -> {
                return "unknown";
            }
        }
    }

    public static List<String> getPossibleRoles() {
        List<String> roles = new ArrayList<>();
        for (Role role : Role.values()) {
            if (role != SUPER_BOSS) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }
}
