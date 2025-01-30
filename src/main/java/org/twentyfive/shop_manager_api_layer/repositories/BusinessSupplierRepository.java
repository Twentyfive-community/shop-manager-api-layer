package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.twentyfive.shop_manager_api_layer.models.BusinessSupplier;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessSupplierId;

import java.util.List;

@Repository
public interface BusinessSupplierRepository extends JpaRepository<BusinessSupplier, BusinessSupplierId> {

    @Query("SELECT s.name FROM BusinessSupplier bs " +
            "JOIN bs.id.supplier s " +
            "WHERE bs.id.business.id = :businessId AND bs.disabled = false")
    List<String> findSupplierNamesByBusinessIdAndDisabledFalse(@Param("businessId") Long businessId);

    // Query che parte dalla tabella BusinessSupplier
    @Query("SELECT s.name FROM BusinessSupplier bs " +
            "JOIN bs.id.supplier s " +  // Unisce la tabella BusinessSupplier alla tabella Supplier
            "WHERE bs.id.business.id = :businessId AND bs.disabled = false " + // Filtro businessId e disabled
            "AND LOWER(s.name) LIKE LOWER(CONCAT('%', :value, '%'))")  // Filtra per valore nel nome
    List<String> findSupplierNamesByBusinessIdAndValueAndDisabledFalse(@Param("businessId") Long businessId, @Param("value") String value);

}
