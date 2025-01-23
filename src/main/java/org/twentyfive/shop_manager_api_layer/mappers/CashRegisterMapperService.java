package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetCashRegisterByDateAndTimeSlotRes;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;

@Service
@RequiredArgsConstructor
public class CashRegisterMapperService {

    private final EntryMapperService entryMapperService;
    private final ComposedEntryMapperService composedEntryMapperService;

    public GetCashRegisterByDateAndTimeSlotRes getByDateAndTimeSlotFromCashRegister(CashRegister cashRegister) {
        GetCashRegisterByDateAndTimeSlotRes res = new GetCashRegisterByDateAndTimeSlotRes();

        res.setId(cashRegister.getId());
        res.setEntryClosures(entryMapperService.mapListEntryClosureToDTO(cashRegister.getEntryClosures()));
        res.setComposedEntryClosures(composedEntryMapperService.mapListComposedEntryClosureToDTO(cashRegister.getComposedEntryClosures()));

        res.setRefTime(cashRegister.getRefTime());
        res.setClosedBy(cashRegister.getClosedBy().getFullName());

        if(cashRegister.getUpdatedBy() != null && cashRegister.getUpdatedTime() != null) {
            res.setUpdatedTime(cashRegister.getUpdatedTime());
            res.setUpdatedBy(cashRegister.getUpdatedBy().getFullName());
        }

        return res;
    }
}
