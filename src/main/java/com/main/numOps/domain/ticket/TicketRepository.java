package com.main.numOps.domain.ticket;

import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketModel,Integer> {

    Page<TicketModel> findByProvider(ProviderModel provider, Pageable pageable);
}
