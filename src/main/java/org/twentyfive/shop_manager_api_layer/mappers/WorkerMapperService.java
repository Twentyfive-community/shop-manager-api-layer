package org.twentyfive.shop_manager_api_layer.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twentyfive.shop_manager_api_layer.models.BusinessWorker;
import org.twentyfive.shop_manager_api_layer.models.Worker;
import org.twentyfive.shop_manager_api_layer.repositories.BusinessWorkerRepository;
import org.twentyfive.shop_manager_api_layer.utilities.classes.simples.SimpleWorker;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Role;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WorkerMapperService {

    private final BusinessWorkerRepository businessWorkerRepository;

    public List<SimpleWorker> mapListSimpleWorkersFromBusinessWorkers (List<BusinessWorker> businessWorkers) {
        List<SimpleWorker> simpleWorkers = new ArrayList<>();
        for (BusinessWorker businessWorker : businessWorkers) {
            SimpleWorker simpleWorker = mapSimpleWorkerFromBusinessWorker(businessWorker);
            simpleWorkers.add(simpleWorker);
        }
        return simpleWorkers;
    }

    public SimpleWorker mapSimpleWorkerFromWorker (Worker worker,Long id) {

        SimpleWorker simpleWorker = new SimpleWorker();
        simpleWorker.setFirstName(worker.getFirstName());
        simpleWorker.setLastName(worker.getLastName());
        simpleWorker.setEmail(worker.getEmail());
        simpleWorker.setRole(Role.getKeycloakRole(businessWorkerRepository.findRoleByWorkerIdAndBusinessId(worker.getId(), id)));
        simpleWorker.setPhoneNumber(worker.getPhoneNumber());
        return simpleWorker;
    }

    private SimpleWorker mapSimpleWorkerFromBusinessWorker(BusinessWorker businessWorker) {
        SimpleWorker simpleWorker = new SimpleWorker();
        simpleWorker.setFirstName(businessWorker.getId().getWorker().getFirstName());
        simpleWorker.setLastName(businessWorker.getId().getWorker().getLastName());
        simpleWorker.setFullName(businessWorker.getId().getWorker().getFullName());
        simpleWorker.setEnabled(!(businessWorker.isDisabled()));
        simpleWorker.setRole(businessWorker.getRole().getRole());
        simpleWorker.setEmail(businessWorker.getId().getWorker().getEmail());
        simpleWorker.setPhoneNumber(businessWorker.getId().getWorker().getPhoneNumber());
        return simpleWorker;
    }

}
