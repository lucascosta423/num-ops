package com.main.numOps.domain.did;

import com.main.numOps.domain.customer.CustomerModel;
import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DidRepository extends JpaRepository<DidModel,Long> {
    Page<DidModel> findByProvider(ProviderModel providerModel, Pageable pageable);

    Page<DidModel> findByCustomer(CustomerModel customer, Pageable pageable);
}
