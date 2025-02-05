package org.twentyfive.shop_manager_api_layer.utilities.statics;

import java.text.DecimalFormat;
import java.util.Locale;

public class ParseUtility {

    public static double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return DecimalFormat.getNumberInstance(Locale.ITALY).parse(value).doubleValue();
        } catch (Exception e) {
            throw new RuntimeException("Error converting value " + value, e);
        }
    }
}
