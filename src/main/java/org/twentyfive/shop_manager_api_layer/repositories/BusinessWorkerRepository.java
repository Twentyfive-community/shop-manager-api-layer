package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessWorkerRepository extends JpaRepository<BusinessWorker, Long> {
    List<BusinessWorker> findById_Business_Id(Long id);

    Optional<BusinessWorker> findById_Business_IdAndId_Worker_KeycloakId(Long id, String keycloakId);

    boolean existsById_Business_IdAndId_Worker_KeycloakId(Long id, String keycloakId);
}
