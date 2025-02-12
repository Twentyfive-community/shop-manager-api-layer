package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.SupplierGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierGroupRepository extends JpaRepository<SupplierGroup, Long> {
    Optional<SupplierGroup> findByBusiness_IdAndName(Long id, String groupName);

    boolean existsByBusiness_IdAndName(Long id, String groupName);

    List<SupplierGroup> findAllByBusiness_Id(Long id);

    @Query("SELECT s.name FROM SupplierGroup s " +
            "WHERE s.business.id = :businessId " +
            "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :value, '%')) " +
            "ORDER BY s.name ASC")
    List<String> findSupplierGroupNamesByBusinessIdAndValue(@Param("businessId") Long businessId, @Param("value") String value);

    List<SupplierGroup> findAllByBusiness_IdAndNameContainsIgnoreCase(Long businessId, String name);
}
