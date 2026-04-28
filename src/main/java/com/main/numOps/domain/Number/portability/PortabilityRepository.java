package com.main.numOps.domain.Number.portability;

import com.main.numOps.Enuns.StatusPortability;
import com.main.numOps.domain.ticket.TicketModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortabilityRepository extends JpaRepository<PortabilityModel, Integer> {

    List<PortabilityModel> findByTicket(TicketModel ticketModel);

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM portability p
        WHERE p.ticket = :ticket
    """)

    void deleteByTicket(@Param("ticket") TicketModel ticket);

    @Modifying
    @Transactional
    @Query("""
        UPDATE portability o 
        SET o.dataAgendamento = COALESCE(:dataAgendamento, o.dataAgendamento),
            o.horaAgendamento = COALESCE(:horaAgendamento, o.horaAgendamento),
            o.status = COALESCE(:status, o.status)
        WHERE o.id IN :ids
    """)
    int updatePortability(
            @Param("ids") List<Integer> ids,
            @Param("dataAgendamento") String dataAgendamento,
            @Param("horaAgendamento") String horaAgendamento,
            @Param("status") StatusPortability status
    );
}


