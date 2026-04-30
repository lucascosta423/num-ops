package com.main.numOps.domain.ticket;

import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketReponse>> listTicket(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ticketService.listTickets(pageable));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TicketModel> createdTicket(@ModelAttribute @Valid TicketRequest request) {
        return ResponseEntity.ok().body(ticketService.save(request));
    }

    @PatchMapping("/cancel/{id}")
    public void cancelTicket(@PathVariable Integer id){
        ticketService.cancel(id);
    }

    @GetMapping("/{id}/fatura")
    public ResponseEntity<InputStreamResource> downloadFatura(@PathVariable Integer id) {

        InputStream inputStream = ticketService.downloadFatura(id);

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
