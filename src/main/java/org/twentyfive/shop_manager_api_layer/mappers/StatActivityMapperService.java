package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetPeriodStatRes;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.models.EntryClosure;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.services.ExpenseService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.*;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatActivityMapperService {

    private final CashRegisterRepository cashRegisterRepository;
    private final EntryMapperService entryMapperService;
    private final ComposedEntryMapperService composedEntryMapperService;

    private final ExpenseService expenseService;

    public List<DailyActivities> mapListDailyActivitiesFromTimeSlots(Long id,
                                                                     List<SimpleTimeSlot> timeSlots,
                                                                     DateRange dateRange) {
        List<DailyActivities> dailyActivities = new ArrayList<>();
        LocalDate dateRef = dateRange.getEnd();

        while (!dateRef.isBefore(dateRange.getStart())) {
            DailyActivities dailyActivity = mapDailyActivitiesFromTimeSlots(id,timeSlots,dateRef);
            dailyActivities.add(dailyActivity);
            dateRef = dateRef.minusDays(1);
        }
        return dailyActivities;
    }

    public PeriodClosure mapPeriodClosureFromTimeSlots(Long id, List<SimpleTimeSlot> timeSlots, DateRange dateRange) {
        double totalPosClosures = 0.0;
        double totalClosingReceipts = 0.0;

        for (SimpleTimeSlot timeSlot : timeSlots) {
            List<CashRegister> cashRegisters = cashRegisterRepository.findAllByRefTimeBetweenAndTimeSlot_NameAndBusiness_Id(dateRange.getStart(), dateRange.getEnd(), timeSlot.getName(), id);

            totalPosClosures += cashRegisters.stream()
                    .flatMap(cashRegister -> cashRegister.getEntryClosures().stream())
                    .filter(entryClosure -> "Chiusura POS".equals(entryClosure.getId().getEntry().getLabel()))
                    .mapToDouble(EntryClosure::getValue)
                    .sum();

            if ("Cena".equals(timeSlot.getName())) {
                totalClosingReceipts += cashRegisters.stream()
                        .flatMap(cashRegister -> cashRegister.getEntryClosures().stream())
                        .filter(entryClosure -> "Chiusura scontrini".equals(entryClosure.getId().getEntry().getLabel()))
                        .mapToDouble(EntryClosure::getValue)
                        .sum();
            }
        }
        return new PeriodClosure(totalClosingReceipts,totalPosClosures,dateRange);

    }

    public List<PeriodStatCashRegister> mapListPeriodCashRegisterFromTimeSlots(Long id, List<SimpleTimeSlot> timeSlots, DateRange dateRange) {
        List<PeriodStatCashRegister> periodStatCashRegisters = new ArrayList<>();

        for (SimpleTimeSlot timeSlot : timeSlots) {
            PeriodStatCashRegister periodStatCashRegister = mapPeriodCashRegisterFromTimeSlotAndDateRange(id,timeSlot,dateRange);
            periodStatCashRegisters.add(periodStatCashRegister);
        }
        return periodStatCashRegisters;
    }

    public PeriodFinancialSummary mapPeriodFinancialSummaryFromTimeSlots(Long id, List<SimpleTimeSlot> timeSlots, DateRange dateRange) {
        PeriodFinancialSummary periodFinancialSummary = new PeriodFinancialSummary();

        GetPeriodStatRes getPeriodStatRes = new GetPeriodStatRes();
        getPeriodStatRes.setPeriodStatCashRegisters(mapListPeriodCashRegisterFromTimeSlots(id,timeSlots,dateRange));

        periodFinancialSummary.setPeriod(dateRange.getStart(), dateRange.getEnd());
        periodFinancialSummary.setTotalRevenueCashRegisters(getPeriodStatRes.getPeriodTotalRevenue());
        periodFinancialSummary.setTotalCostCashRegisters(getPeriodStatRes.getPeriodCost());


        periodFinancialSummary.setTotalCostSuppliers(expenseService.getTotalExpensesInDateRange(id,dateRange));
        return periodFinancialSummary;
    }


    private PeriodStatCashRegister mapPeriodCashRegisterFromTimeSlotAndDateRange(Long id,SimpleTimeSlot timeSlot, DateRange dateRange) {
        PeriodStatCashRegister periodStatCashRegister = new PeriodStatCashRegister();

        periodStatCashRegister.setTimeSlot(timeSlot.getName());

        List<CashRegister> cashRegisters = cashRegisterRepository.findAllByRefTimeBetweenAndTimeSlot_NameAndBusiness_Id(dateRange.getStart(), dateRange.getEnd(), timeSlot.getName(), id);

        double totalRevenue = cashRegisters.stream()
                .mapToDouble(cashRegister -> cashRegister.getReport().getTotalRevenue())
                .sum();
        double totalCost = cashRegisters.stream().mapToDouble(cashRegister -> cashRegister.getReport().getTotalCost()).sum();

        periodStatCashRegister.setTotalRevenue(totalRevenue);
        periodStatCashRegister.setTotalCost(totalCost);
        periodStatCashRegister.setTotal(totalRevenue,totalCost);

        return periodStatCashRegister;
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
