package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessWorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;

@RequiredArgsConstructor
@Service
public class BusinessWorkerService {
    private final BusinessWorkerRepository businessWorkerRepository;

    public boolean existsByKeycloakIdAndBusinessId(String keycloakId, Long businessId) {
        if(!businessWorkerRepository.existsById_Business_IdAndId_Worker_KeycloakId(businessId, keycloakId)) {
            throw new BusinessWorkerNotFoundException("KeycloakId " +keycloakId+ " non associato a questo business id: " +businessId);
        }
        return true;
    }
}
