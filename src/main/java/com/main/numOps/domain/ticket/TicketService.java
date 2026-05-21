package com.main.numOps.domain.ticket;

import com.main.numOps.domain.portability.PortabilityService;
import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import com.main.numOps.domain.ticket.enuns.TicketStatus;
import com.main.numOps.utils.AuthUtils;
import com.main.numOps.utils.DateUtils;
import com.main.storage.factory.StorageFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final PortabilityService portabilityService;
    private final StorageFactory storageFactory;
    private final AuthUtils authUtils;


    public TicketService(TicketRepository ticketRepository, PortabilityService portabilityService, StorageFactory storageFactory, AuthUtils authUtils) {
        this.ticketRepository = ticketRepository;
        this.portabilityService = portabilityService;
        this.storageFactory = storageFactory;
        this.authUtils = authUtils;
    }

    @Transactional
    public TicketModel save(TicketRequest ticketRequest) {

        TicketModel ticket = new TicketModel();

        TicketModel saved = ticketRepository.save(ticket);

        switch (saved.getType()) {
            case PORTABILITY -> {
                saved.setFatura(uploadFatura(ticketRequest, saved.getTicket()));
                portabilityService.createPortabilityNumbers(saved,ticketRequest.numeros());
            }
            case DID ->
                    System.out.println("OK DID");
            case CANCELLATION ->
                    System.out.println("OK Cancelamento");
        }

        return saved;
    }

    public TicketReponse findById(Long id) {
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

    public void cancel(Long id) {

        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        if (ticket.getStatus() == TicketStatus.COMPLETED) {
            throw new IllegalStateException("Não é possível cancelar um ticket finalizado");
        }

        ticket.setStatus(TicketStatus.CANCELED);
        ticket.setCancelBy(authUtils.getCurrentUser().getEmail());
        ticket.setCancelAt(DateUtils.nowWithoutNanos());

        TicketModel saved = ticketRepository.save(ticket);

        storageFactory.getStorage().delete(saved.getFatura());

        switch (saved.getType()) {
            case PORTABILITY -> portabilityService.deleteByTicket(saved);

            case DID -> System.out.println("Ticket Did Cancelado!");

            case CANCELLATION -> System.out.println("Ticket Cancelamento Cancelado!");
        }
    }

    private Page<TicketReponse> findByProvider(ProviderModel provider, Pageable pageable) {
        return ticketRepository.findByProvider(provider, pageable)
                .map(TicketReponse::fromEntity);
    }

    private String uploadFatura(TicketRequest request, String ticket) {
        try {
            MultipartFile file = request.fatura();

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Fatura é obrigatória");
            }

            if (!"application/pdf".equalsIgnoreCase(file.getContentType()) || !isPdf(file)) {
                throw new RuntimeException("Arquivo deve ser um PDF válido");
            }

            String fileName = ticket + ".pdf";

            try (InputStream is = file.getInputStream()) {
                return storageFactory.getStorage().upload(is, fileName);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da fatura", e);
        }
    }

    public InputStream downloadFatura(Long ticketId) {
        TicketModel ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        if (ticket.getFatura() == null) {
            throw new RuntimeException("Este ticket não possui fatura");
        }

        return storageFactory.getStorage().download(ticket.getFatura());
    }

    private boolean isPdf(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {

            byte[] header = new byte[5];
            if (is.read(header) != 5) {
                return false;
            }

            String headerStr = new String(header);
            return "%PDF-".equals(headerStr);

        } catch (Exception e) {
            return false;
        }
    }
}
