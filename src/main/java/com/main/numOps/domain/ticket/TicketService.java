package com.main.numOps.domain.ticket;

import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import com.main.numOps.mapper.TicketMapper;
import com.main.numOps.utils.AuthUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void save(TicketRequest ticketRequest){

        TicketModel ticket = mapper.toModel(ticketRequest);

        var ticketSaved = ticketRepository.save(ticket);

        var type = ticketSaved.getType();


    }

    public TicketModel findById(Integer id){
        return ticketRepository.findById(id).orElseThrow();
    }

    public Page<TicketReponse> listTickets(Pageable pageable){
        if (authUtils.isAdmin()) {
            return ticketRepository.findAll(pageable)
                    .map(TicketReponse::fromEntity);
        }

        var provider = authUtils.getCurrentUser().getProvider();
        return findByProvider(provider, pageable);
    }

    public void cancel(Integer id){
        ticketRepository.deleteById(id);
    }

    private Page<TicketReponse> findByProvider(ProviderModel provider, Pageable pageable){
        return ticketRepository.findByProvider(provider,pageable)
                .map(TicketReponse::fromEntity);
    }
}
