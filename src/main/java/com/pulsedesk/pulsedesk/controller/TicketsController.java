package com.pulsedesk.pulsedesk.controller;

import com.pulsedesk.pulsedesk.model.Ticket;
import com.pulsedesk.pulsedesk.service.TicketsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketsController {
    private final TicketsService ticketsService;

    public TicketsController(TicketsService ticketsService){
        this.ticketsService = ticketsService;
    }
    @GetMapping
    public List<Ticket> getAllTickets(){
        return ticketsService.getAllTickets();
    }

    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable Long ticketId){
        return ticketsService.getTicketById(ticketId);
    }
}
