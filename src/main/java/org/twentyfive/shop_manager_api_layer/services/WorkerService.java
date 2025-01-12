package org.twentyfive.shop_manager_api_layer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager_api_layer.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager_api_layer.exceptions.WorkerNotFoundException;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.repositories.WorkerRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final BusinessService businessService;
    private final KeycloakService keycloakService;

    public Worker getById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }

    public Worker getByKeycloakId(String keycloakId) {
        return workerRepository.findByKeycloakId(keycloakId).orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }

    public Boolean add(AddWorkerReq addWorkerReq) {
        Business business = businessService.getById(addWorkerReq.getBusinessId());
        Worker worker = addWorkerReq.getWorker();
        keycloakService.addEmployeeToRealm(worker);
        return assignWorkerToABusiness(business, worker);
    }


    public List<Worker> getAllByBusinessId(Long id){
        return workerRepository.findByWorkFor_Id(id);
    }

    public Boolean AddInExistentBusiness(AddInExistentBusinessReq addInExistentBusinessReq) {
        Business business = businessService.getById(addInExistentBusinessReq.getBusinessId());
        Worker worker = getById(addInExistentBusinessReq.getWorkerId());
        return assignWorkerToABusiness(business, worker);
    }

    private Boolean assignWorkerToABusiness(Business business, Worker worker) {

        if(worker.getWorkFor() == null){
            worker.setWorkFor(new HashSet<>());
        }

        worker.getWorkFor().add(business);
        business.getWorkers().add(worker);

        workerRepository.save(worker);
        businessService.add(business);
        return true;
    }
}
