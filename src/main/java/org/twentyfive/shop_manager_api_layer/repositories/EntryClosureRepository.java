package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.EntryClosure;


@Repository
public interface EntryClosureRepository extends JpaRepository<EntryClosure, Long> {

}
