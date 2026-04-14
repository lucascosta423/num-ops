package com.main.numOps.domain.requestPortability;

import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestPortabilityRepository extends JpaRepository<RequestPortabilityModel, String> {
    Page<RequestPortabilityModel> findByProvedor(ProviderModel provedor, Pageable pageable);
}
