package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.ChangeRoleReq;
import org.twentyfive.shop_manager_api_layer.exceptions.BusinessWorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.RoleNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.WorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessWorkerId;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Role;
import org.twentyfive.shop_manager_api_layer.utilities.classes.filters.WorkerFilter;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BusinessWorkerService {

    private final KeycloakService keycloakService;
    private final BusinessWorkerRepository businessWorkerRepository;

    public boolean existsByKeycloakIdAndBusinessId(String keycloakId, Long businessId) {
        if(!businessWorkerRepository.existsById_Business_IdAndId_Worker_KeycloakIdAndDisabledFalse(businessId, keycloakId)) {
            throw new BusinessWorkerNotFoundException("KeycloakId " +keycloakId+ " not associated or suspended at business id: " +businessId);
        }
        return true;
    }

    public BusinessWorker getByEmailAndBusinessId(String email, Long businessId) {
        return businessWorkerRepository.findById_Business_IdAndId_Worker_Email(businessId, email).orElseThrow(() -> new WorkerNotFoundException("Worker not found with email: " + email +
                " or with this business id: " + businessId));
    }

    public BusinessWorker createBusinessWorkerFromAdd(Business business, Worker worker, String role) {
        BusinessWorkerId businessWorkerId = new BusinessWorkerId(business, worker);
        BusinessWorker businessWorker = new BusinessWorker(businessWorkerId,role);
        businessWorkerRepository.save(businessWorker);
        return businessWorker;
    }

    public List<BusinessWorker> getAllBusinessWorkersFiltered(Long id, WorkerFilter filter) {

        return businessWorkerRepository.findByBusinessIdAndRoleAndFullName(
                id,
                filter.getRole() != null ? Role.fromString(filter.getRole()) : null,
                filter.getName() != null ? filter.getName() : null
        );

    }

    public Boolean changeRole(Long id, ChangeRoleReq changeRoleReq) {
        BusinessWorker businessWorker =getByEmailAndBusinessId(changeRoleReq.getEmail(), id);
        if(Role.getPossibleRoles().contains(changeRoleReq.getRole())) {
            String bearerToken = keycloakService.getAdminBearerToken();
            String keycloakId = businessWorker.getId().getWorker().getKeycloakId();
            String role = Role.getKeycloakRole(businessWorker.getRole().getRole());

            keycloakService.removeRoleFromUser(bearerToken,keycloakId,role);
            businessWorker.setRole(Role.fromString(changeRoleReq.getRole()));
            keycloakService.addRoleToUser(bearerToken,keycloakId,Role.getKeycloakRole(changeRoleReq.getRole()));
            businessWorkerRepository.save(businessWorker);

            return true;
        }
        throw new RoleNotFoundException("this role +" +changeRoleReq.getRole() + "doesn't exist or it's not applicable!");
    }

    public Boolean toggleStatus(Long id, String email) {
        BusinessWorker businessWorker = getByEmailAndBusinessId(email, id);
        businessWorker.setDisabled(!businessWorker.isDisabled());
        return businessWorkerRepository.save(businessWorker) != null;

    }
    public BusinessWorker getByBusinessIdAndKeycloakId(Long businessId, String keycloakId){
        return businessWorkerRepository.findById_Business_IdAndId_Worker_KeycloakIdAndDisabledFalse(businessId, keycloakId).
                orElseThrow(() -> new BusinessWorkerNotFoundException("KeycloakId " +keycloakId+ " disabilitato per questo business id: " +businessId));
    }
}
