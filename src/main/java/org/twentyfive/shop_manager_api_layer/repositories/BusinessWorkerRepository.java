package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;

import java.util.List;

@Repository
public interface BusinessWorkerRepository extends JpaRepository<BusinessWorker, Long> {
    List<BusinessWorker> findById_Business_Id(Long id);
}
