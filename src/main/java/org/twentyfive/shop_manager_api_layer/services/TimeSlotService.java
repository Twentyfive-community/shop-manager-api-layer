package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddTimeSlotReq;
import org.twentyfive.shop_manager_api_layer.exceptions.TimeSlotNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;
import org.twentyfive.shop_manager_api_layer.repositories.TimeSlotRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final BusinessService businessService;


    public Boolean add(AddTimeSlotReq addTimeSlotReq) {
        Business business = businessService.getById(addTimeSlotReq.getBusinessId());
        TimeSlot timeSlot = addTimeSlotReq.getTimeSlot();

        timeSlot.setBusiness(business);
        timeSlotRepository.save(timeSlot);
        return true;
    }

    public List<TimeSlot> getAllByBusinessId(Long id) {
        return timeSlotRepository.findByBusiness_Id(id);

    }

    public TimeSlot getByNameAndBusinessId(String timeSlotName, Long businessId) {
        return timeSlotRepository.findByNameAndBusiness_Id(timeSlotName,businessId).orElseThrow(()-> new TimeSlotNotFoundException("timeSlot not found"));
    }
}
