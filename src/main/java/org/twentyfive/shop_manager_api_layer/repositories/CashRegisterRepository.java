package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    List<CashRegister> findAllByBusiness_Id(Long id);

    Boolean existsByRefTimeAndTimeSlot_Id(LocalDate refTime, Long timeSlotId);
}
