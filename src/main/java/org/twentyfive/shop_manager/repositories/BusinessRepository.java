package org.twentyfive.shop_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager.models.Business;
import org.twentyfive.shop_manager.models.Worker;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
}
