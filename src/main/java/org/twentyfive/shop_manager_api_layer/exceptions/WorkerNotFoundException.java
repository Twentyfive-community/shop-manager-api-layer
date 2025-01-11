package org.twentyfive.shop_manager_api_layer.exceptions;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(String message) {
        super(message);
    }
}
