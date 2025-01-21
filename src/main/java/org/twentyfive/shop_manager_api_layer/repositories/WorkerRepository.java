package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Worker;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    //List<Worker> findByWorkFor_Id(Long businessId);

    Optional<Worker> findByKeycloakId(String keycloakId);
    List<Worker> findByEmail();
    List<Worker> getAllWorker();
}
