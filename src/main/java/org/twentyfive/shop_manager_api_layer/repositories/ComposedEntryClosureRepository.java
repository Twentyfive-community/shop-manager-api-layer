package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntryClosure;

@Repository
public interface ComposedEntryClosureRepository extends JpaRepository<ComposedEntryClosure, Long> {
}
