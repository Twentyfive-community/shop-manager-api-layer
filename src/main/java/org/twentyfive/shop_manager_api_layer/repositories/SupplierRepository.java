package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Supplier;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    Optional<Supplier> findByNameAndWorkFor_Id(String name, Long businessId);

    @Query("SELECT s.name FROM Business b JOIN b.suppliers s WHERE b.id = :businessId AND LOWER(s.name) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<String> findSupplierNamesByBusinessIdAndValue(@Param("businessId") Long businessId, @Param("value") String value);

}
