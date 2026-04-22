package com.main.numOps.domain.ticket;

import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import com.main.numOps.domain.ticket.enuns.TicketStatus;
import com.main.numOps.mapper.TicketMapper;
import com.main.numOps.utils.AuthUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;
    private final AuthUtils authUtils;


    public TicketService(TicketRepository ticketRepository, TicketMapper mapper, AuthUtils authUtils) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
        this.authUtils = authUtils;
    }

    @Transactional
    public TicketModel save(TicketRequest ticketRequest) {

        TicketModel ticket = mapper.toModel(ticketRequest);

        TicketModel saved = ticketRepository.save(ticket);

        switch (saved.getType()) {
            case PORTABILITY -> System.out.println("OK Portabilidade");
            case DID -> System.out.println("OK DID");
            case CANCELLATION -> System.out.println("OK Cancelamento");
        }

        return saved;
    }

    public TicketReponse findById(Integer id) {
        return ticketRepository.findById(id)
                .map(TicketReponse::fromEntity)
                .orElseThrow();
    }

    public Page<TicketReponse> listTickets(Pageable pageable) {
        if (authUtils.isAdmin()) {
            return ticketRepository.findAll(pageable)
                    .map(TicketReponse::fromEntity);
        }

        var provider = authUtils.getCurrentUser().getProvider();
        return findByProvider(provider, pageable);
    }

    public void cancel(Integer id) {

        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        if (ticket.getStatus() == TicketStatus.COMPLETED) {
            throw new IllegalStateException("Não é possível cancelar um ticket finalizado");
        }

        ticket.setStatus(TicketStatus.CANCELED);
        ticket.setCancelRequestedBy(authUtils.getCurrentUser());
        ticket.setCancelRequestedAt(LocalDateTime.now());

        TicketModel saved = ticketRepository.save(ticket);

        switch (saved.getType()){
            case PORTABILITY -> System.out.print("Ticket Cancelado!");

            case DID -> System.out.println("Ticket Cancelado!");

            case CANCELLATION -> System.out.println("Ticket Cancelado!");
        }
    }

    private Page<TicketReponse> findByProvider(ProviderModel provider, Pageable pageable) {
        return ticketRepository.findByProvider(provider, pageable)
                .map(TicketReponse::fromEntity);
    }
}
