package org.twentyfive.shop_manager_api_layer.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodFinancialSummary;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodStat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPeriodStatRes {
    private PeriodStat periodStat;
    private PeriodClosure periodClosure;
    private PeriodFinancialSummary periodFinancialSummary;
}
