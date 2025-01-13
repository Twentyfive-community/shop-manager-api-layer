package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddCashRegisterReq;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashRegisterService {
    private final CashRegisterRepository cashRegisterRepository;

    private final BusinessService businessService;
    private final WorkerService workerService;
    private final TimeSlotService timeSlotService;
    private final EntryService entryService;
    private final ComposedEntryService composedEntryService;

    public Boolean add(AddCashRegisterReq addCashRegisterReq) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();

        Business business = businessService.getById(addCashRegisterReq.getBusinessId());
        Worker worker = workerService.getByKeycloakId(keycloakId);
        TimeSlot timeSlot = timeSlotService.getByNameAndBusinessId(addCashRegisterReq.getTimeSlotName(), addCashRegisterReq.getBusinessId());

        CashRegister cashRegister = new CashRegister();
        cashRegister.setRefTime(addCashRegisterReq.getCashRegisterDate());
        cashRegister.setBusiness(business);
        cashRegister.setTimeSlot(timeSlot);
        cashRegister.setClosedBy(worker);

        CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);

        entryService.createAndAddListOfEntryClosure(addCashRegisterReq.getEntries(),savedCashRegister);
        composedEntryService.createAndAddListOfComposedEntryClosure(addCashRegisterReq.getComposedEntries(),savedCashRegister);
        return true;
    }

    public List<CashRegister> getAllByBusinessId(Long id) {
        return cashRegisterRepository.findAllByBusiness_Id(id);
    }
}
