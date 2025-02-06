package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Supplier;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SupplierAndGroupCheck;

import java.util.List;
import java.util.Optional;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByBusinessIdAndDisabledFalseOrderByNameAsc(Long businessId);
    Optional<Supplier> findByBusinessIdAndName(Long businessId, String name);

    boolean existsByBusinessIdAndNameAndDisabledTrue(Long businessId, String name);

    Boolean existsByBusinessIdAndId(Long businessId, Long id);

    List<Supplier> findAllByBusinessIdAndNameInAndDisabledFalse(Long businessId, List<String> supplierNames);

    @Query("""
    SELECT s FROM Supplier s 
    LEFT JOIN s.group g 
    WHERE s.business.id = :businessId 
    AND s.disabled = false 
    AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :value, '%')) 
         OR LOWER(g.name) LIKE LOWER(CONCAT('%', :value, '%')))
""")
    List<Supplier> findSuppliersByBusinessIdAndValueAndDisabledFalse(@Param("businessId") Long businessId, @Param("value") String value);

    @Query("SELECT s.name " +
            "FROM Supplier s " +
            "WHERE s.business.id = :id AND s.disabled = false " +
            "ORDER BY s.name ASC")
    List<String> findAllNamesByIdAndDisabledFalse(Long id);

    @Query("""
    SELECT new org.twentyfive.shop_manager_api_layer.utilities.classes.SupplierAndGroupCheck(
         s.name,
         CASE 
             WHEN (:name IS NOT NULL AND g IS NOT NULL AND g.name = :name) THEN true 
             ELSE false 
         END
    )
    FROM Supplier s LEFT JOIN s.group g
    WHERE s.business.id = :id
      AND s.disabled = false
      AND (
            (:name IS NULL AND g IS NULL) 
         OR (:name IS NOT NULL AND (g IS NULL OR g.name = :name))
      )
    ORDER BY 
         CASE 
             WHEN (:name IS NOT NULL AND g IS NOT NULL AND g.name = :name) THEN 0 
             ELSE 1 
         END,
         s.name ASC
""")
    List<SupplierAndGroupCheck> getAllNamesByBusiness_IdAndGroupNullOrNameAndDisabledFalse(@Param("id") Long id, @Param("name") String name);

    boolean existsByBusiness_IdAndName(Long id, String name);

}
