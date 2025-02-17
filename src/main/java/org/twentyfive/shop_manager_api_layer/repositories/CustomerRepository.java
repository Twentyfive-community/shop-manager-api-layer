package org.twentyfive.shop_manager_api_layer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twentyfive.shop_manager_api_layer.models.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByBusinessIdAndCompanyNameContainsIgnoreCaseAndDisabledFalseOrderByCompanyNameAsc(Long id, String name);

    Boolean existsByBusinessIdAndCompanyNameAndDisabledTrue(Long id, String name);

    Optional<Customer> findByBusinessIdAndCompanyName(Long id, String companyName);

    boolean existsByBusiness_IdAndCompanyName(Long id, String companyName);
}
