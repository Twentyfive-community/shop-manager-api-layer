package org.twentyfive.shop_manager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager.dtos.requests.AddInExistentBusinessReq;
import org.twentyfive.shop_manager.dtos.requests.AddWorkerReq;
import org.twentyfive.shop_manager.exceptions.WorkerNotFoundException;
import org.twentyfive.shop_manager.models.Business;
import org.twentyfive.shop_manager.models.Worker;
import org.twentyfive.shop_manager.repositories.WorkerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final BusinessService businessService;

    public Worker getById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new WorkerNotFoundException("Worker not found"));
    }
    public Boolean addWorker(AddWorkerReq addWorkerReq) {
        Business business = businessService.getById(addWorkerReq.getBusinessId());
        Worker worker = addWorkerReq.getWorker();
        return assignWorkerToABusiness(business, worker);
    }


    public List<Worker> getAllWorkersByBusinessId(Long id) {
        return workerRepository.findByWorkFor_Id(id);
    }

    public Boolean AddInExistentBusiness(AddInExistentBusinessReq addInExistentBusinessReq) {
        Business business = businessService.getById(addInExistentBusinessReq.getBusinessId());
        Worker worker = getById(addInExistentBusinessReq.getWorkerId());
        return assignWorkerToABusiness(business, worker);
    }

    private Boolean assignWorkerToABusiness(Business business, Worker worker) {
        if(worker.getWorkFor() == null){
            worker.setWorkFor(new ArrayList<>());
        }

        worker.getWorkFor().add(business);
        business.getWorkers().add(worker);

        workerRepository.save(worker);
        businessService.add(business);
        return true;
    }
}
