package com.main.numOps.domain.Number.portability;

import com.main.numOps.domain.ticket.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PortabilityRepository extends JpaRepository<PortabilityModel, Integer> {

    List<PortabilityModel> findByTicket(TicketModel ticketModel);

    @Modifying
    @Query("""
    UPDATE portability p
    SET p.status = 'CANCELED'
    WHERE p.ticket = :ticket
""")
    void cancelByTicket(@Param("ticket") TicketModel ticket);
}
