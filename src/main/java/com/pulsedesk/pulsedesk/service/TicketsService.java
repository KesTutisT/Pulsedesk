package com.pulsedesk.pulsedesk.service;

import com.pulsedesk.pulsedesk.exception.ResourceNotFoundException;
import com.pulsedesk.pulsedesk.model.Ticket;
import com.pulsedesk.pulsedesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsService {
    private final TicketRepository ticketRepository;

    public TicketsService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id){
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with an id: " + id));
    }
}
