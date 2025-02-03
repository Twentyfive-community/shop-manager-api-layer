package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.SupplierGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierGroupRepository extends JpaRepository<SupplierGroup, Long> {
    Optional<SupplierGroup> findByBusiness_IdAndName(Long id, String groupName);

    boolean existsByBusiness_IdAndName(Long id, String groupName);

    List<SupplierGroup> findAllByBusiness_Id(Long id);
}
