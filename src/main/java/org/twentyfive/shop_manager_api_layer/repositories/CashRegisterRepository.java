package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    List<CashRegister> findAllByBusiness_Id(Long id);

    Boolean existsByRefTimeAndTimeSlot_Id(LocalDate refTime, Long timeSlotId);

    Optional<CashRegister> findByBusiness_IdAndTimeSlot_NameAndRefTime(Long id, String timeSlotName,LocalDate refTime);

    List<CashRegister> findAllByRefTimeBetween(LocalDate startDate, LocalDate endDate);

}
