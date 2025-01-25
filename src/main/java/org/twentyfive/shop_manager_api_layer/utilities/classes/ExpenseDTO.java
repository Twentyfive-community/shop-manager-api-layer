package org.twentyfive.shop_manager_api_layer.utilities.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String refTime;
    private String paymentMethod;
    private String doneBy;

    private String supplier;
    private String value;

    public void setRefTime(LocalDate refTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.refTime = refTime.format(formatter);
    }

    public void setValue(double value){
        this.value = String.format("%.2f", value);
    }
}
