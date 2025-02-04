package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleTimeSlot;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotMapperService {

    public List<SimpleTimeSlot> createSimpleTimeSlotsFromTimeSlots(List<TimeSlot> timeSlots){
        List<SimpleTimeSlot> simpleTimeSlots = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            SimpleTimeSlot simpleTimeSlot = createSimpleTimeSlotFromTimeSlot(timeSlot);
            simpleTimeSlots.add(simpleTimeSlot);
        }
        return simpleTimeSlots;
    }

    private SimpleTimeSlot createSimpleTimeSlotFromTimeSlot(TimeSlot timeSlot){
        SimpleTimeSlot simpleTimeSlot = new SimpleTimeSlot();
        simpleTimeSlot.setId(timeSlot.getId());
        simpleTimeSlot.setName(timeSlot.getName());
        simpleTimeSlot.setDescription(timeSlot.getDescription());
        simpleTimeSlot.setStart(timeSlot.getStart());
        simpleTimeSlot.setEnd(timeSlot.getEnd());
        return simpleTimeSlot;
    }
}
