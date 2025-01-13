package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.TimeSlot;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByBusiness_Id(Long id);

    Optional<TimeSlot> findByNameAndBusiness_Id(String timeSlotName, Long businessId);
}
