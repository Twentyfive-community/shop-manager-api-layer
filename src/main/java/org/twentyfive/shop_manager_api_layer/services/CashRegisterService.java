package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCashRegisterReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.GetByDateAndTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.mappers.DailyActivityMapperService;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CashRegisterDTO;
import org.twentyfive.shop_manager_api_layer.exceptions.CashRegisterNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.CashRegisterMapperService;
import org.twentyfive.shop_manager_api_layer.models.*;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterLogRepository;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DailyActivities;
import org.twentyfive.shop_manager_api_layer.utilities.classes.DateRange;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleTimeSlot;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashRegisterService {
    private final CashRegisterRepository cashRegisterRepository;
    private final CashRegisterLogRepository cashRegisterLogRepository;

    private final BusinessService businessService;
    private final WorkerService workerService;
    private final TimeSlotService timeSlotService;
    private final EntryService entryService;
    private final ComposedEntryService composedEntryService;

    private final CashRegisterMapperService cashRegisterMapperService;
    private final DailyActivityMapperService dailyActivityMapperService;

    public Boolean add(AddCashRegisterReq addCashRegisterReq) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();

        // Recupera il business associato
        Business business = businessService.getById(addCashRegisterReq.getBusinessId());
        Worker worker = workerService.getByKeycloakId(keycloakId);
        TimeSlot timeSlot = timeSlotService.getByNameAndBusinessId(addCashRegisterReq.getTimeSlotName(), addCashRegisterReq.getBusinessId());

        Optional<CashRegister> optCashRegister =cashRegisterRepository.findByBusiness_IdAndTimeSlot_NameAndRefTime(addCashRegisterReq.getBusinessId(), addCashRegisterReq.getTimeSlotName(), addCashRegisterReq.getCashRegisterDate());

        if (optCashRegister.isEmpty()) {
            CashRegister cashRegister = new CashRegister();
            cashRegister.setRefTime(addCashRegisterReq.getCashRegisterDate());
            cashRegister.setBusiness(business);
            cashRegister.setTimeSlot(timeSlot);
            cashRegister.setClosedBy(worker);

            CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);

            // Crea voci Entry e Composed Entry
            if(addCashRegisterReq.getEntries() != null){
                entryService.createAndAddListOfEntryClosure(addCashRegisterReq.getEntries(), savedCashRegister);
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

            cashRegister.setUpdatedBy(worker); // Aggiorna il campo updatedBy
            cashRegister.setUpdatedAt(LocalDateTime.now());

            // Salva la CashRegister aggiornata
            CashRegister updatedCashRegister = cashRegisterRepository.save(cashRegister);

            // Aggiorna le voci Entry e Composed Entry
            entryService.updateAndRemoveEntryClosure(addCashRegisterReq.getEntries(), updatedCashRegister);
            //composedEntryService.updateListOfComposedEntryClosure(addCashRegisterReq.getComposedEntries(), updatedCashRegister);

            // Crea il log per l'aggiornamento
            createLog(cashRegister);
        }

        return true;
    }

    private void createLog(CashRegister cashRegister) {
        CashRegisterLog log = new CashRegisterLog();
        log.setCashRegister(cashRegister);

        log.setClosedBy(cashRegister.getClosedBy().getFullName());
        log.setCreatedAt(LocalDateTime.now());

        log.setUpdatedAt(LocalDateTime.now()); // Solo se hai bisogno di un timestamp
        log.setUpdatedBy(cashRegister.getUpdatedBy().getFullName());
        cashRegisterLogRepository.save(log);
    }

    public List<CashRegister> getAllByBusinessId(Long id) {
        return cashRegisterRepository.findAllByBusiness_Id(id);
    }

    public CashRegisterDTO getByDateAndTimeSlot(Long id, GetByDateAndTimeSlotReq request) {

        CashRegister cashRegister = cashRegisterRepository.findByBusiness_IdAndTimeSlot_NameAndRefTime(id, request.getTimeSlotName(),request.getRefTime()).orElseThrow(() -> new CashRegisterNotFoundException("Can't find cash register with this businessId: " +id+ " and this time slot name: " +request.getTimeSlotName()));

        return cashRegisterMapperService.mapCashRegisterDTOFromCashRegister(cashRegister);

    }
    public List<CashRegister> getAllCashRegistersInAPeriod(DateRange dateRange){
        return cashRegisterRepository.findAllByRefTimeBetween(dateRange.getStart(), dateRange.getEnd());
    }
    public Page<DailyActivities> getPeriodDailyActivities(Long id, int page, int size, DateRange dateRange) {

        List<SimpleTimeSlot> timeSlots = timeSlotService.getAllByBusinessId(id);
        Pageable pageable = PageRequest.of(page, size);
        List<DailyActivities> dailyActivities = dailyActivityMapperService.mapListDailyActivitiesFromTimeSlots(id,timeSlots,dateRange);
        return PageUtility.convertListToPage(dailyActivities,pageable);
    }


}
