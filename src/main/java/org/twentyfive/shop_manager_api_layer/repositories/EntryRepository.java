package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Entry;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Optional<Entry> findByLabelAndBusiness(String label, Business business);

    List<Entry> findAllByBusinessOrderByIdAsc(Business business);
}
