package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.CustomerExpense;

@Repository
public interface CustomerExpenseRepository extends JpaRepository<CustomerExpense, Long> {
}
