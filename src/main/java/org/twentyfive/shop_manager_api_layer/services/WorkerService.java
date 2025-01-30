package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.ChangeRoleReq;
import org.twentyfive.shop_manager_api_layer.exceptions.RoleNotFoundException;
import org.twentyfive.shop_manager_api_layer.exceptions.WorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.WorkerMapperService;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessWorkerId;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.repositories.WorkerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Role;
import org.twentyfive.shop_manager_api_layer.utilities.classes.statics.PageUtility;
import org.twentyfive.shop_manager_api_layer.utilities.statics.JwtUtility;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final BusinessWorkerRepository businessWorkerRepository;

    private final BusinessService businessService;
    private final KeycloakService keycloakService;

    private final WorkerMapperService workerMapperService;

    public Worker getById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException("Worker not found with id: " + id));
    }

    public Worker getByKeycloakId(String keycloakId) {
        return workerRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new WorkerNotFoundException("Worker not found with keycloakId: " + keycloakId));
    }
    public BusinessWorker getByEmailAndBusinessId(String email, Long businessId) {
        return businessWorkerRepository.findById_Business_IdAndId_Worker_Email(businessId, email).orElseThrow(() -> new WorkerNotFoundException("Worker not found with email: " + email +
                " or with this business id: " + businessId));
    }

    public SimpleWorker getSimpleWorkerFromToken(Long id) throws IOException {
        String keycloakId = JwtUtility.getIdKeycloak();
        Worker worker = getByKeycloakId(keycloakId);
        return workerMapperService.mapSimpleWorkerFromWorker(worker,id);
    }

    public Boolean add(AddWorkerReq addWorkerReq) {
        Business business = businessService.getById(addWorkerReq.getBusinessId());

        Worker newWorker = createWorkerFromAdd(addWorkerReq.getWorker());
        List<Worker> allWorkers = workerRepository.findAll();

        for(Worker worker: allWorkers) {
            if(Objects.equals(worker.getEmail(), newWorker.getEmail())){
                return false;
            }
        }
        keycloakService.addEmployeeToRealm(newWorker, Role.getKeycloakRole(addWorkerReq.getWorker().getRole()));
        Worker workerDB = workerRepository.save(newWorker);

        createBusinessWorkerFromAdd(business,workerDB,addWorkerReq.getWorker().getRole());
        return true;
    }


    public Page<SimpleWorker> getAllByBusinessId(Long id,int page, int size){
        List<BusinessWorker> businessWorkers = businessWorkerRepository.findById_Business_Id(id);
        List<SimpleWorker> simpleWorkers = workerMapperService.mapListSimpleWorkersFromBusinessWorkers(businessWorkers);

        Pageable pageable = PageRequest.of(page, size);

        return PageUtility.convertListToPage(simpleWorkers, pageable);
    }

    public Boolean AddInExistentBusiness(AddInExistentBusinessReq addInExistentBusinessReq) {
        Business business = businessService.getById(addInExistentBusinessReq.getBusinessId());
        Worker worker = getById(addInExistentBusinessReq.getWorkerId());
        createBusinessWorkerFromAdd(business,worker,addInExistentBusinessReq.getRole());
        return true;
    }

    private Worker createWorkerFromAdd(SimpleWorker simpleWorker) {
        Worker worker = new Worker();
        worker.setFirstName(simpleWorker.getFirstName());
        worker.setLastName(simpleWorker.getLastName());
        worker.setEmail(simpleWorker.getEmail());
        worker.setPhoneNumber(simpleWorker.getPhoneNumber());
        return worker;
    }

    private BusinessWorker createBusinessWorkerFromAdd(Business business, Worker worker, String role) {
        BusinessWorkerId businessWorkerId = new BusinessWorkerId(business, worker);
        BusinessWorker businessWorker = new BusinessWorker(businessWorkerId,role);
        businessWorkerRepository.save(businessWorker);
        return businessWorker;
    }

    public List<String> getRoles() {
        return Role.getPossibleRoles();
    }

    public Boolean changeRole(Long id, ChangeRoleReq changeRoleReq) {
        BusinessWorker businessWorker = getByEmailAndBusinessId(changeRoleReq.getEmail(), id);
        if(getRoles().contains(changeRoleReq.getRole())) {
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
}
