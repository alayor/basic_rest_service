package com.alayor.api_service.model.responses;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.entities.User;

public class PurchasedTicketWithUser
{
    private final User user;
    private final PurchasedTicket purchasedTicket;

    public PurchasedTicketWithUser(User user, PurchasedTicket purchasedTicket)
    {
        this.user = user;
        this.purchasedTicket = purchasedTicket;
    }

    public User getUser()
    {
        return user;
    }

    public PurchasedTicket getPurchasedTicket()
    {
        return purchasedTicket;
    }
}
