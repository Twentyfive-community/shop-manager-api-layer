package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.services.ExpenseService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyActivityMapperService {

    private final CashRegisterRepository cashRegisterRepository;
    private final EntryMapperService entryMapperService;
    private final ComposedEntryMapperService composedEntryMapperService;

    public List<DailyActivities> mapListDailyActivitiesFromTimeSlots(Long id,
                                                                     List<SimpleTimeSlot> timeSlots,
                                                                     DateRange dateRange) {
        List<DailyActivities> dailyActivities = new ArrayList<>();
        LocalDate dateRef = dateRange.getEnd();

        while(dateRef.isAfter(dateRange.getStart()) || dateRef.isEqual(dateRange.getStart())) {
            DailyActivities dailyActivity = mapDailyActivitiesFromTimeSlots(id,timeSlots,dateRef);
            dailyActivities.add(dailyActivity);
            dateRef = dateRef.minusDays(1);
        }
        return dailyActivities;
    }

    private DailyActivities mapDailyActivitiesFromTimeSlots(Long id,List<SimpleTimeSlot> timeSlots, LocalDate dateRef) {
        DailyActivities dailyActivity = new DailyActivities();
        dailyActivity.setRawDate(dateRef);
        List<DailyCashRegister> dailyCashRegisters = new ArrayList<>();
        for (SimpleTimeSlot timeSlot : timeSlots) {
            Optional<CashRegister> optCashRegister = cashRegisterRepository.findByBusiness_IdAndTimeSlot_NameAndRefTime(id, timeSlot.getName(), dateRef);
            DailyCashRegister dailyCashRegister = mapDailyCashRegisterWithTimeSlotAndDateRef(optCashRegister,timeSlot);
            dailyCashRegisters.add(dailyCashRegister);
        }
        dailyActivity.setCashRegisters(dailyCashRegisters);
        return dailyActivity;
    }

    private DailyCashRegister mapDailyCashRegisterWithTimeSlotAndDateRef(Optional<CashRegister> optCashRegister, SimpleTimeSlot timeSlot) {
        DailyCashRegister dailyCashRegister = new DailyCashRegister();
        if(optCashRegister.isPresent()) {
            CashRegister cashRegister = optCashRegister.get();
            dailyCashRegister.setId(cashRegister.getId());
            dailyCashRegister.setTimeSlot(timeSlot.getName());
            dailyCashRegister.setTotalRevenue(cashRegister.getReport().getTotalRevenue());
            dailyCashRegister.setTotalCost(cashRegister.getReport().getTotalCost());
            dailyCashRegister.setTotal(cashRegister.getReport().getTotalRevenue(),cashRegister.getReport().getTotalCost());

            dailyCashRegister.setEntryClosureDetails(entryMapperService.mapEntryClosureToDetailsDTO(cashRegister.getEntryClosures()));
            dailyCashRegister.setComposedEntryClosureDetails(composedEntryMapperService.mapComposedEntryClosureToDetailsDTO(cashRegister.getComposedEntryClosures()));

            dailyCashRegister.setDone(true);
        } else {
            dailyCashRegister.setId(-1L);
            dailyCashRegister.setTimeSlot(timeSlot.getName());
            dailyCashRegister.setTotalRevenue(0L);
            dailyCashRegister.setTotalCost(0L);
            dailyCashRegister.setTotal(0L,0L);
            dailyCashRegister.setDone(false);
        }
        return dailyCashRegister;
    }
}
