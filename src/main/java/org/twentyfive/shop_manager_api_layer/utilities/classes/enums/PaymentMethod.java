package org.twentyfive.shop_manager_api_layer.utilities.classes.enums;

public enum PaymentMethod {
    CONTANTI("Contanti"),
    CARTA_DI_CREDITO("Carta di Credito"),
    BONIFICO_BANCARIO("Bonifico Bancario"),
    PAYPAL("PayPal"),
    ASSEGNO("Assegno"),
    CRYPTO_VALUTA("Crypto Valuta");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.getValue().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Nessuna modalità di pagamento trovata per il valore: " + value);
    }
}
