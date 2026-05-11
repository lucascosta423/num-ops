package com.main.numOps.domain.customer;

import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<CustomerModel, Long> {
    Page<CustomerModel> findByProvider(ProviderModel provider, Pageable pageable);
}
