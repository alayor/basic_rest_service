package com.alayor.api_service;

import com.alayor.api_service.model.responses.PurchasedTicketWithUser;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.PurchasedTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Service used to retrieve tickets.
 */
@Component
public class PurchasedTicketsService
{

    private final PurchasedTicketRepository purchasedTicketsRepository;

    @Autowired
    public PurchasedTicketsService(PurchasedTicketRepository purchasedTicketsRepository)
    {
        this.purchasedTicketsRepository = purchasedTicketsRepository;
    }

    /**
     * Retrieve all the tickets along with the user information.
     * @return All the tickets and the user that bought them.
     */
    public ServiceResult<List<PurchasedTicketWithUser>> getAllPurchasedTicketsWithUsers()
    {
        List<Optional<PurchasedTicketWithUser>> optionalTicketsByUser = purchasedTicketsRepository.getAllPurchasedTicketsByUser();
        List<PurchasedTicketWithUser> ticketsByUser = optionalTicketsByUser
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        return new ServiceResult<>(true, "", ticketsByUser);
    }
}
