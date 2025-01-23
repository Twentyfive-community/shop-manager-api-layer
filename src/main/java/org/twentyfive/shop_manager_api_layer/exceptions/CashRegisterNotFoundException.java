package org.twentyfive.shop_manager_api_layer.exceptions;

public class CashRegisterNotFoundException extends RuntimeException {
    public CashRegisterNotFoundException(String message) {
        super(message);
    }
}
