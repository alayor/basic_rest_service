package com.alayor.api_service.model.responses;

public class OfferRS
{
    private String amount;
    private String currency;
    private String from;
    private String to;
    private final boolean isPurchased;
    private boolean purchased;

    public OfferRS(String amount, String currency, String from, String to, boolean isPurchased)
    {
        this.amount = amount;
        this.currency = currency;
        this.from = from;
        this.to = to;
        this.isPurchased = isPurchased;
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

    public boolean isPurchased()
    {
        return isPurchased;
    }

    public void setPurchased(boolean purchased)
    {
        this.purchased = purchased;
    }
}
