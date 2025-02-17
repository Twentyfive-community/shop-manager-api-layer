package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Expense;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> , JpaSpecificationExecutor<Expense> {

    @Query("SELECT e FROM Expense e " +
            "WHERE e.worker.id.business.id = :businessId " +
            "AND e.refTime BETWEEN :startDate AND :endDate " +
            "AND (:supplierName IS NULL OR e.supplier.name ILIKE %:supplierName%) " +
            "AND (:paymentMethod IS NULL OR e.paymentMethod = :paymentMethod) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "ORDER BY e.refTime DESC")
    List<Expense> findFilteredExpenses(
            @Param("businessId") Long businessId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("supplierName") String supplierName,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("paid") Boolean paid
    );


    List<Expense> findByWorker_Id_Business_IdAndRefTime(Long id, LocalDate date);
}
