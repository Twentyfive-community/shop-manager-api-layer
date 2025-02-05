package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodStatCashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.statics.ParseUtility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPeriodStatRes {
    private String period;
    private String periodTotalRevenue;
    private String periodCost;
    private String periodTotal;

    private List<PeriodStatCashRegister> periodStatCashRegisters;

    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(Locale.ITALY);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00", SYMBOLS);

    public String getPeriodCost() {
        return calculateTotal(PeriodStatCashRegister::getTotalCost);
    }

    public String getPeriodTotalRevenue() {
        return calculateTotal(PeriodStatCashRegister::getTotalRevenue);
    }

    public String getPeriodTotal() {
        return calculateTotal(PeriodStatCashRegister::getTotal);
    }

    private String calculateTotal(Function<PeriodStatCashRegister, String> getter) {
        double sum = periodStatCashRegisters.stream()
                .mapToDouble(register -> ParseUtility.parseDouble(getter.apply(register)))
                .sum();
        return DECIMAL_FORMAT.format(sum);
    }

    public void setPeriod(LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.period = start.format(formatter) + " - " + end.format(formatter);
    }
}
