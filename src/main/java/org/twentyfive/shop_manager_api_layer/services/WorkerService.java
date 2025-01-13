package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager_api_layer.exceptions.WorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.mappers.WorkerMapperService;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessWorkerId;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.repositories.WorkerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final BusinessWorkerRepository businessWorkerRepository;

    private final BusinessService businessService;
    private final KeycloakService keycloakService;

    private final WorkerMapperService workerMapperService;

    public Worker getById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }

    public Worker getByKeycloakId(String keycloakId) {
        return workerRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }

    public Boolean add(AddWorkerReq addWorkerReq) {
        Business business = businessService.getById(addWorkerReq.getBusinessId());

        Worker worker = createWorkerFromAdd(addWorkerReq.getWorker());
        keycloakService.addEmployeeToRealm(worker, addWorkerReq.getRole());
        Worker workerDB = workerRepository.save(worker);

        createBusinessWorkerFromAdd(business,workerDB,addWorkerReq.getRole());

        return true;
    }


    public List<SimpleWorker> getAllByBusinessId(Long id){
        List<BusinessWorker> businessWorkers = businessWorkerRepository.findById_Business_Id(id);
        return workerMapperService.mapListSimpleWorkersFromBusinessWorkers(businessWorkers);
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
}
