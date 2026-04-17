package com.main.numOps.domain.ticket.enuns;

import com.main.numOps.domain.ticket.TicketService;
import com.main.numOps.domain.ticket.dtos.TicketReponse;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping
    public void createdTicket(@RequestBody TicketRequest request){
        ticketService.save(request);
    }
}
