package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCashRegisterReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetByDateAndTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.dtos.responses.GetPeriodStatRes;
import org.twentyfive.shop_manager_api_layer.utilities.classes.PeriodStat;
import org.twentyfive.shop_manager_api_layer.mappers.StatActivityMapperService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.*;
import org.twentyfive.shop_manager_api_layer.exceptions.CashRegisterNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.CashRegisterMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterLogRepository;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.MsUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;
    private final CashRegisterLogRepository cashRegisterLogRepository;

    private final TimeSlotService timeSlotService;
    private final EntryService entryService;
    private final ComposedEntryService composedEntryService;

    private final CashRegisterMapperService cashRegisterMapperService;
    private final StatActivityMapperService statActivityMapperService;
    private final MsUserClient msUserClient;

    public Boolean add(AddCashRegisterReq addCashRegisterReq,
                       String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        MsUser msUser = msUserClient.getUserFromToken(authorization);
        TimeSlot timeSlot = timeSlotService.getByNameAndBusinessId(addCashRegisterReq.getTimeSlotName(), business.getId());

        Optional<CashRegister> optCashRegister =cashRegisterRepository.findByBusiness_IdAndTimeSlot_NameAndRefTime(business.getId(), addCashRegisterReq.getTimeSlotName(), addCashRegisterReq.getCashRegisterDate());

        if (optCashRegister.isEmpty()) {
            CashRegister cashRegister = new CashRegister();
            cashRegister.setRefTime(addCashRegisterReq.getCashRegisterDate());
            cashRegister.setBusiness(business);
            cashRegister.setTimeSlot(timeSlot);
            cashRegister.setClosedBy(msUser);
            cashRegister.setUpdatedBy(msUser);
            CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);

            // Crea voci Entry e Composed Entry
            if(addCashRegisterReq.getEntries() != null){
                entryService.createAndAddListOfEntryClosure(addCashRegisterReq.getEntries(), savedCashRegister, business);
            }
            if(addCashRegisterReq.getComposedEntries() != null){
                composedEntryService.createAndAddListOfComposedEntryClosure(addCashRegisterReq.getComposedEntries(), savedCashRegister);
            }

        } else {
            CashRegister cashRegister = optCashRegister.get();

            // Aggiorna i campi modificabili
            cashRegister.setRefTime(addCashRegisterReq.getCashRegisterDate());
            cashRegister.setBusiness(business);
            cashRegister.setTimeSlot(timeSlot);

            cashRegister.setUpdatedBy(msUser); // Aggiorna il campo updatedBy

            // Salva la CashRegister aggiornata
            CashRegister updatedCashRegister = cashRegisterRepository.save(cashRegister);

            // Aggiorna le voci Entry e Composed Entry
            entryService.updateAndRemoveEntryClosure(addCashRegisterReq.getEntries(), updatedCashRegister, business);
            composedEntryService.updateAndRemoveComposedEntryClosure(addCashRegisterReq.getComposedEntries(), updatedCashRegister);

            // Crea il log per l'aggiornamento
            createLog(cashRegister);
        }

        return true;
    }

    private void createLog(CashRegister cashRegister) {
        CashRegisterLog log = new CashRegisterLog();
        log.setCashRegister(cashRegister);

        log.setRefTime(cashRegister.getRefTime());
        log.setTimeSlotName(cashRegister.getTimeSlot().getName());

        log.setClosedBy(cashRegister.getClosedBy().getFullName());
        log.setCreatedAt(LocalDateTime.now());

        log.setUpdatedAt(LocalDateTime.now()); // Solo se hai bisogno di un timestamp
        log.setUpdatedBy(cashRegister.getUpdatedBy().getFullName());
        cashRegisterLogRepository.save(log);
    }

    public List<CashRegister> getAll(String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        return cashRegisterRepository.findAllByBusiness(business);
    }

    public CashRegisterDTO getByDateAndTimeSlot(String authorization, GetByDateAndTimeSlotReq request) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        CashRegister cashRegister = cashRegisterRepository.findByBusiness_IdAndTimeSlot_NameAndRefTime(business.getId(), request.getTimeSlotName(),request.getRefTime()).orElse(null);

        return cashRegisterMapperService.mapCashRegisterDTOFromCashRegister(cashRegister);

    }
    public List<CashRegister> getAllCashRegistersInAPeriod(DateRange dateRange){
        return cashRegisterRepository.findAllByRefTimeBetween(dateRange.getStart(), dateRange.getEnd());
    }
    public Page<DailyActivities> getPeriodDailyActivities(String authorization, int page, int size, DateRange dateRange) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        List<SimpleTimeSlot> timeSlots = timeSlotService.getAll(business.getId());
        Pageable pageable = PageRequest.of(page, size);
        List<DailyActivities> dailyActivities = statActivityMapperService.mapListDailyActivitiesFromTimeSlots(business.getId(),timeSlots,dateRange);
        return PageUtility.convertListToPage(dailyActivities,pageable);
    }

    public PeriodStat getPeriodStat(Long id, DateRange dateRange) throws IOException {
        PeriodStat res = new PeriodStat();

        res.setPeriod(dateRange.getStart(), dateRange.getEnd());

        List<SimpleTimeSlot> timeSlots = timeSlotService.getAll(id);

        List<PeriodStatCashRegister> periodStatCashRegisters = statActivityMapperService.mapListPeriodCashRegisterFromTimeSlots(id,timeSlots,dateRange);

        res.setPeriodStatCashRegisters(periodStatCashRegisters);

        return res;


    }


    public CashRegisterDetails getDetailsById(Long id) {
        CashRegister cashRegister = cashRegisterRepository.findById(id).orElseThrow(() -> new CashRegisterNotFoundException("Cash Register not found with id: " + id));
        return cashRegisterMapperService.mapCashRegisterDetailsFromCashRegister(cashRegister);

    }

    public PeriodClosure getPeriodClosure(Long id, DateRange dateRange) throws IOException {
        List<SimpleTimeSlot> timeSlots = timeSlotService.getAll(id);
        return statActivityMapperService.mapPeriodClosureFromTimeSlots(id, timeSlots,dateRange);

    }

    public PeriodFinancialSummary getPeriodFinancialSummary(Long id, DateRange dateRange) throws IOException {
        List<SimpleTimeSlot> timeSlots = timeSlotService.getAll(id);
        return statActivityMapperService.mapPeriodFinancialSummaryFromTimeSlots(id, timeSlots,dateRange);
    }

    public GetPeriodStatRes getPeriodStats(String authorization, DateRange dateRange) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        return new GetPeriodStatRes(getPeriodStat(business.getId(), dateRange),getPeriodClosure(business.getId(), dateRange),getPeriodFinancialSummary(business.getId(), dateRange));
    }
}
