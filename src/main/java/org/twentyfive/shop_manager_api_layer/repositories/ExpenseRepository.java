package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Expense;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByWorker_Business_IdAndRefTimeBetweenAndSupplier_NameContainsIgnoreCaseOrderByRefTimeDesc(
            Long businessId,
            LocalDate startDate,
            LocalDate endDate,
            String name
    );

    List<Expense> findByWorker_Business_IdAndRefTimeBetweenAndPaidAndSupplier_NameContainsIgnoreCaseOrderByRefTimeDesc(
            Long businessId,
            LocalDate startDate,
            LocalDate endDate,
            boolean paid,
            String name
    );

    List<Expense> findByWorker_Business_IdAndRefTime(Long id, LocalDate date);
}
