package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.twentyfive.shop_manager_api_layer.models.CashRegisterLog;

public interface CashRegisterLogRepository extends CrudRepository<CashRegisterLog, Long> {
}
