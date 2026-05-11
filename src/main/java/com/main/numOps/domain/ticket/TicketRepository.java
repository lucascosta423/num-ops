package com.main.numOps.domain.ticket;

import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel,Long> {

    Page<TicketModel> findByProvider(ProviderModel provider, Pageable pageable);
}
