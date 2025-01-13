package org.twentyfive.shop_manager_api_layer.mappers;

import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.utilities.classes.SimpleWorker;

import java.util.ArrayList;
import java.util.List;


@Service
public class WorkerMapperService {
    public List<SimpleWorker> mapListSimpleWorkersFromBusinessWorkers (List<BusinessWorker> businessWorkers) {
        List<SimpleWorker> simpleWorkers = new ArrayList<>();
        for (BusinessWorker businessWorker : businessWorkers) {
            SimpleWorker simpleWorker = mapSimpleWorkerFromBusinessWorker(businessWorker.getId().getWorker());
            simpleWorkers.add(simpleWorker);
        }
        return simpleWorkers;
    }

    private SimpleWorker mapSimpleWorkerFromBusinessWorker(Worker worker) {
        SimpleWorker simpleWorker = new SimpleWorker();
        simpleWorker.setFirstName(worker.getFirstName());
        simpleWorker.setLastName(worker.getLastName());
        simpleWorker.setEmail(worker.getEmail());
        simpleWorker.setPhoneNumber(worker.getPhoneNumber());
        return simpleWorker;
    }
}
