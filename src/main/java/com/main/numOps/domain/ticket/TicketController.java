package com.main.numOps.domain.ticket;

import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketReponse>> listTicket(
            @PageableDefault(page = 0,size = 10,direction = Sort.Direction.ASC) Pageable pageable
    ){
        return ResponseEntity.ok(ticketService.findAll(pageable));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createdTicket(@ModelAttribute @Valid TicketRequest request){
        ticketService.save(request);
    }
}
