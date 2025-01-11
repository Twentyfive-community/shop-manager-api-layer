package org.twentyfive.shop_manager_api_layer.utilities;

public enum Operation {
    ADDITION("+"),
    SUBTRACTION("-");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
