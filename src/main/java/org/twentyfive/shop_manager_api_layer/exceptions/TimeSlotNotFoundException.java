package org.twentyfive.shop_manager_api_layer.exceptions;

public class TimeSlotNotFoundException extends RuntimeException {
    public TimeSlotNotFoundException(String message) {
        super(message);
    }
}
