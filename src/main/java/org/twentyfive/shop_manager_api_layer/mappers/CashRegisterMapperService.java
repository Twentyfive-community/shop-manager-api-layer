package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CashRegisterDTO;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CashRegisterDetails;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodClosure;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashRegisterMapperService {

    private final EntryMapperService entryMapperService;
    private final ComposedEntryMapperService composedEntryMapperService;

    public CashRegisterDTO mapCashRegisterDTOFromCashRegister(CashRegister cashRegister) {
        CashRegisterDTO res = new CashRegisterDTO();

        res.setId(cashRegister.getId());
        res.setEntryClosures(entryMapperService.mapListEntryClosureToDTO(cashRegister.getEntryClosures()));
        res.setComposedEntryClosures(composedEntryMapperService.mapListComposedEntryClosureToDTO(cashRegister.getComposedEntryClosures()));

        res.setRefTime(cashRegister.getRefTime());
        res.setClosedBy(cashRegister.getClosedBy().getFullName());

        if(cashRegister.getUpdatedBy() != null) {
            res.setUpdatedBy(cashRegister.getUpdatedBy().getFullName());
        }

        return res;
    }

    public List<CashRegisterDTO> mapListCashRegisterDTOsFromListCashRegisters(List<CashRegister> cashRegisters) {
        List<CashRegisterDTO> cashRegisterDTOs = new ArrayList<>();
        for (CashRegister cashRegister : cashRegisters) {
            CashRegisterDTO cashRegisterDTO =mapCashRegisterDTOFromCashRegister(cashRegister);
            cashRegisterDTOs.add(cashRegisterDTO);
        }
        return cashRegisterDTOs;
    }

    public CashRegisterDetails mapCashRegisterDetailsFromCashRegister(CashRegister cashRegister) {
        CashRegisterDetails cashRegisterDetails = new CashRegisterDetails();

        cashRegisterDetails.setId(cashRegister.getId());
        cashRegisterDetails.setTimeSlotName(cashRegister.getTimeSlot().getName());
        cashRegisterDetails.setRefTime(cashRegister.getRefTime());

        cashRegisterDetails.setTotalRevenue(cashRegister.getReport().getTotalRevenue());
        cashRegisterDetails.setTotalCost(cashRegister.getReport().getTotalCost());
        cashRegisterDetails.setTotal(cashRegister.getReport().getTotalRevenue(),cashRegister.getReport().getTotalCost());

        cashRegisterDetails.setFirstModifiedWorker(cashRegister.getClosedBy().getFullName());
        cashRegisterDetails.setFirstModifiedDate(cashRegister.getCreatedAt());

        cashRegisterDetails.setLastModifiedWorker(cashRegister.getUpdatedBy().getFullName());
        cashRegisterDetails.setLastModifiedDate(cashRegister.getUpdatedAt());

        cashRegisterDetails.setEntryClosureDetails(entryMapperService.mapEntryClosureToDetailsDTO(cashRegister.getEntryClosures()));
        cashRegisterDetails.setComposedEntryClosureDetails(composedEntryMapperService.mapComposedEntryClosureToDetailsDTO(cashRegister.getComposedEntryClosures()));

        return cashRegisterDetails;
    }
}
