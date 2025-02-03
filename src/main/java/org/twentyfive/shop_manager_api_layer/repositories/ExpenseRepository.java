package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Expense;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByWorker_Id_Business_IdAndRefTimeBetweenOrderByRefTimeDesc(
            Long businessId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Expense> findByWorker_Id_Business_IdAndRefTime(Long id, LocalDate date);
}
