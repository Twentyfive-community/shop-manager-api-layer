package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Supplier;


@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {


}
