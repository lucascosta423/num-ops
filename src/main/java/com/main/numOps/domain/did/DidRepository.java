package com.main.numOps.domain.did;

import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidRepository extends JpaRepository<DidModel,Integer> {

    Page<DidModel> findByProvider(ProviderModel provider, Pageable pageable);

}
