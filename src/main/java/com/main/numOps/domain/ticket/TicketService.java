package com.main.numOps.domain.ticket;

import com.main.numOps.domain.Number.did.NumberService;
import com.main.numOps.domain.Number.portability.PortabilityService;
import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.providers.ProviderService;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import com.main.numOps.domain.ticket.enuns.TicketType;
import com.main.numOps.mapper.TicketMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ProviderService providerService;
    private final TicketMapper mapper;
    private final PortabilityService portabilityService;
    private final NumberService numberService;

    public TicketService(TicketRepository ticketRepository, ProviderService providerService, TicketMapper mapper, PortabilityService portabilityService, NumberService numberService) {
        this.ticketRepository = ticketRepository;
        this.providerService = providerService;
        this.mapper = mapper;
        this.portabilityService = portabilityService;
        this.numberService = numberService;
    }

    public void save(TicketRequest ticketRequest){

        TicketModel ticketModel = mapper.toModel(ticketRequest);

        var type = ticketModel.getType().getDescriptor();

        var ticket = ticketRepository.save(ticketModel);

        switch (TicketType.valueOf(type)) {
            case PORTABILITY:
                portabilityService.createPortabilityNumbers(ticket,ticketRequest.numeros());
                break;

            case DID:
                numberService.activateNumber(ticket, ticketRequest.numeros());
                break;

            case CANCELLATION:
                return;

            default:
                throw new IllegalArgumentException("Ticket type not supported");
        }
    }

    public TicketModel findById(Integer id){
        return ticketRepository.findById(id).orElseThrow();
    }

    public Page<TicketModel> findByProvider(ProviderModel provider, Pageable pageable){
        return ticketRepository.findByProvider(provider,pageable);
    }

    public Page<TicketModel> findAll(Pageable pageable){
        return ticketRepository.findAll(pageable);
    }

    public void cancel(Integer id){
        ticketRepository.deleteById(id);
    }
}
