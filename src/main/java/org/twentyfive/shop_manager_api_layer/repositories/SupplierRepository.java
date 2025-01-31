package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Supplier;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s.name FROM Supplier s " +
            "WHERE s.business.id = :businessId " +
            "AND s.disabled = false " +
            "ORDER BY s.name ASC")
    List<String> findSupplierNamesByBusinessIdAndDisabledFalse(@Param("businessId") Long businessId);

    @Query("SELECT s.name FROM Supplier s " +
            "WHERE s.business.id = :businessId " +
            "AND s.disabled = false " +
            "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :value, '%')) " +
            "ORDER BY s.name ASC")
    List<String> findSupplierNamesByBusinessIdAndValueAndDisabledFalse(@Param("businessId") Long businessId, @Param("value") String value);

    Optional<Supplier> findByBusinessIdAndName(Long businessId, String name);

    boolean existsByBusinessIdAndName(Long businessId, String name);
}
