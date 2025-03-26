package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.clients.MsUserClient;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.exceptions.TimeSlotNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.TimeSlotMapperService;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;
import org.twentyfive.shop_manager_api_layer.repositories.CashRegisterRepository;
import org.twentyfive.shop_manager_api_layer.repositories.TimeSlotRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.CheckCashRegister;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    private final TimeSlotMapperService timeSlotMapperService;
    private final CashRegisterRepository cashRegisterRepository;
    private final MsUserClient msUserClient;

    public Boolean add(AddTimeSlotReq addTimeSlotReq,String authorization) throws IOException {
        Business business = msUserClient.getBusinessFromToken(authorization);
        TimeSlot timeSlot = createTimeSlotFromSimpleTimeSlot(addTimeSlotReq.getTimeSlot());
        timeSlot.setBusiness(business);
        timeSlotRepository.save(timeSlot);
        return true;
    }

    public List<SimpleTimeSlot> getAllByBusinessId(Long id) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByBusiness_Id(id);
        return timeSlotMapperService.createSimpleTimeSlotsFromTimeSlots(timeSlots);
    }

    public TimeSlot getByNameAndBusinessId(String timeSlotName, Long businessId) {
        return timeSlotRepository.findByNameAndBusiness_Id(timeSlotName,businessId).orElseThrow(()-> new TimeSlotNotFoundException("timeSlot not found"));
    }

    private TimeSlot createTimeSlotFromSimpleTimeSlot(SimpleTimeSlot simpleTimeSlot) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setName(simpleTimeSlot.getName());
        timeSlot.setDescription(simpleTimeSlot.getDescription());
        timeSlot.setStart(simpleTimeSlot.getStart());
        timeSlot.setEnd(simpleTimeSlot.getEnd());
        return timeSlot;
    }

    public List<CheckCashRegister> checkCashRegisterInTimeSlot(Long businessId, LocalDate date){
        List<SimpleTimeSlot> timeSlots = getAllByBusinessId(businessId);
        List<CheckCashRegister> checkCashRegisters = new ArrayList<>();
        for (SimpleTimeSlot timeSlot : timeSlots) {
            CheckCashRegister checkCashRegister = new CheckCashRegister();
            checkCashRegister.setTimeSlotName(timeSlot.getName());
            checkCashRegister.setDone(cashRegisterRepository.existsByRefTimeAndTimeSlot_Id(date,timeSlot.getId()));
            checkCashRegisters.add(checkCashRegister);
        }
        return checkCashRegisters;
    }
}
