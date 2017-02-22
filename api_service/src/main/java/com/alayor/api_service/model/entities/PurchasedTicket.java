package com.alayor.api_service.model.entities;

public class PurchasedTicket
{
    private final String userId;
    private final String amount;
    private final String currency;
    private final String from;
    private final String to;

    public PurchasedTicket(String userId, String amount, String currency, String from, String to)
    {

        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.from = from;
        this.to = to;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }
}
