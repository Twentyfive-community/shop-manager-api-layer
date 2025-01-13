package org.twentyfive.shop_manager_api_layer.exceptions;

public class ComposedEntryNotFoundException extends RuntimeException {
    public ComposedEntryNotFoundException(String message) {
        super(message);
    }
}
