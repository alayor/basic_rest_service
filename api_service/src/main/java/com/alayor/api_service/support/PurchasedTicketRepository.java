package com.alayor.api_service.support;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.responses.PurchasedTicketWithUser;

import java.util.List;
import java.util.Optional;

/**
 * Represents functionality to save tickets in some repository.
 */
public interface PurchasedTicketRepository
{
    /**
     * Represents a support for inserting purchased ticket information in the repository.
     * @param purchasedTicket
     * @return
     */
    long insert(PurchasedTicket purchasedTicket);

    /**
     * REpresent support to retrieve all tickets purchased along with the user that bought the ticket.
     * @return
     */
    List<Optional<PurchasedTicketWithUser>> getAllPurchasedTicketsByUser();
}
