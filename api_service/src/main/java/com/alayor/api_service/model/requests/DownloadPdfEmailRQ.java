package com.alayor.api_service.model.requests;

import org.json.JSONObject;

public class DownloadPdfEmailRQ
{
    private final String userId;
    private final String amount;
    private final String currency;
    private final String from;
    private final String to;
    private final String quantity;

    public DownloadPdfEmailRQ(String userId, String amount, String currency, String from, String to, String quantity)
    {

        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.from = from;
        this.to = to;
        this.quantity = quantity;
    }

    public DownloadPdfEmailRQ(String json)
    {
        JSONObject jsonObject = new JSONObject(json);
        this.userId = jsonObject.getString("userId");
        this.amount = jsonObject.getString("amount");
        this.currency = jsonObject.getString("currency");
        this.from = jsonObject.getString("from");
        this.to = jsonObject.getString("to");
        this.quantity = jsonObject.getString("quantity");
    }

    public String getUserId()
    {
        return userId;
    }

    public String getCurrency()
    {
        return currency;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public String getQuantity()
    {
        return quantity;
    }
}
