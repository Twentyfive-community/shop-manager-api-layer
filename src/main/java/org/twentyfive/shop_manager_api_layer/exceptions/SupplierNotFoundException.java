package org.twentyfive.shop_manager_api_layer.exceptions;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(String message) {
        super(message);
    }
}
