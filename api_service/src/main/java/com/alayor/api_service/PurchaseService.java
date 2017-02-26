package com.alayor.api_service;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.PurchasedTicketRepository;
import com.alayor.api_service.support.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
public class PurchaseService {
    private final PurchasedTicketRepository purchasedTicketsRepository;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseService(PurchasedTicketRepository purchasedTicketRepository, UserRepository userRepository) {
        this.purchasedTicketsRepository = purchasedTicketRepository;
        this.userRepository = userRepository;
    }

    public ServiceResult<Optional> buyTicket(BuyTicketRQ buyTicketRQ) {
        Optional<User> userById = userRepository.getUserById(buyTicketRQ.getUserId());

        if (!userById.isPresent()) {
            throw new RuntimeException("Ticket was not purchased successfully. Please contact our support department.");
        }
        long ticketId = purchasedTicketsRepository.insert(createPurchasedTicket(buyTicketRQ));
        if (ticketId <= 0) {
            throw new RuntimeException("Ticket was not purchased successfully. Please contact our support department.");
        }
        return new ServiceResult<>(true,
                "Ticket from " + buyTicketRQ.getFrom() + " to " + buyTicketRQ.getTo() + " was successfully bought.", empty());
    }

    private PurchasedTicket createPurchasedTicket(BuyTicketRQ buyTicketRQ) {
        return new PurchasedTicket(
                buyTicketRQ.getUserId(),
                buyTicketRQ.getAmount(),
                buyTicketRQ.getCurrency(),
                buyTicketRQ.getFrom(),
                buyTicketRQ.getTo()
        );
    }
}
