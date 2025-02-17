package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.CustomerExpense;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerExpenseRepository extends JpaRepository<CustomerExpense, Long> {
    List<CustomerExpense> findByWorker_Id_Business_IdAndRefTimeBetweenOrderByRefTimeDesc(Long id, LocalDate start, LocalDate end);
}
