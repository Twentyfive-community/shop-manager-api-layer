package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExpenseDTO {
    private Long id;
    private String refTime;
    private String paymentMethod;
    private String doneBy;
    private boolean issued;
    private String customer;
    private String value;

    public void setRefTime(LocalDate refTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.refTime = refTime.format(formatter);
    }

    public void setValue(double value){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        this.value = df.format(value);
    }
}
