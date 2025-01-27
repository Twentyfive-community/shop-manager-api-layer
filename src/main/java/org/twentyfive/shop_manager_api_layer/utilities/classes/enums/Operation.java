package org.twentyfive.shop_manager_api_layer.utilities.classes.enums;

public enum Operation {
    ADDITION("+"),
    SUBSTRACTION("-"),
    NEUTRAL("~"),
    SUB_TO_TOTAL("'");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
