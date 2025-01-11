package org.twentyfive.shop_manager_api_layer.exceptions;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(String message) {
        super(message);
    }
}
