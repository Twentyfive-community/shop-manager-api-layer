package org.twentyfive.shop_manager_api_layer.exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String message) {
        super(message);
    }
}
