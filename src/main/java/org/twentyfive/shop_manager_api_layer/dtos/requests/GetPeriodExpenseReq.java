package org.twentyfive.shop_manager_api_layer.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.filters.ExpenseFilter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPeriodExpenseReq {
    private DateRange dateRange;

    private ExpenseFilter expenseFilter;
}
