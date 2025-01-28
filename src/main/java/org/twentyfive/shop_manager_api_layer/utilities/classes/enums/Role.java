package org.twentyfive.shop_manager_api_layer.utilities.classes.enums;

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
}
