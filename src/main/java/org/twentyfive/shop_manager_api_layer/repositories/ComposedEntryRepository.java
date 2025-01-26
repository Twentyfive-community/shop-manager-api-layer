package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComposedEntryRepository extends JpaRepository<ComposedEntry, Long> {
    Optional<ComposedEntry> findByLabel(String label);

    List<ComposedEntry> findAllByOrderByIdAsc();
}
