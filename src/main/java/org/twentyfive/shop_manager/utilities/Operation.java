package org.twentyfive.shop_manager.utilities;

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
