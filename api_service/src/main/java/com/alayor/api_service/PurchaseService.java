package com.alayor.api_service;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.AirlineServiceClient;
import com.alayor.api_service.support.PurchasedTicketRepository;
import com.alayor.api_service.support.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
public class PurchaseService
{
    private final PurchasedTicketRepository purchasedTicketsRepository;
    private final AirlineServiceClient airlineServiceClient;
    private final UserRepository userRepository;

    @Autowired
    public PurchaseService(PurchasedTicketRepository purchasedTicketRepository, AirlineServiceClient airlineServiceClient, UserRepository userRepository)
    {
        this.purchasedTicketsRepository = purchasedTicketRepository;
        this.airlineServiceClient = airlineServiceClient;
        this.userRepository = userRepository;
    }

    public ServiceResult<Optional> buyTicket(BuyTicketRQ buyTicketRQ)
    {
        Optional<User> userById = userRepository.getUserById(buyTicketRQ.getUserId());

        if (!userById.isPresent())
        {
            throw new RuntimeException("Ticket was not purchased successfully. Please contact our support department.");
        }
        JSONObject jsonObject = airlineServiceClient.buyTicket(userById.get().getAccountId(), buyTicketRQ.getQuantity(), buyTicketRQ.getFrom(), buyTicketRQ.getTo());
        if (jsonObject.has("message"))
        {
            return new ServiceResult<>(false, jsonObject.getString("message"), empty());
        }
        long ticketId = purchasedTicketsRepository.insert(createPurchasedTicket(buyTicketRQ));
        if (ticketId <= 0)
        {
            throw new RuntimeException("Ticket was not purchased successfully. Please contact our support department.");
        }
        return new ServiceResult<>(true,
                "Ticket from " + buyTicketRQ.getFrom() + " to " + buyTicketRQ.getTo() +" was successfully bought.", empty());
    }

    private PurchasedTicket createPurchasedTicket(BuyTicketRQ buyTicketRQ)
    {
        return new PurchasedTicket(
                    buyTicketRQ.getUserId(),
                    buyTicketRQ.getAmount(),
                    buyTicketRQ.getCurrency(),
                    buyTicketRQ.getFrom(),
                    buyTicketRQ.getTo()
            );
    }
}
