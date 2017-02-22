package com.alayor.api_service.support;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents functionality to user external airline services.
 */
public interface AirlineServiceClient
{
    /**
     * Creates a new account in the external system.
     * @param currency used to create the account.
     * @return The result of the account creation in JSON format.
     */
    JSONObject createNewAccount(String currency);

    /**
     * Deposits money in the specified account.
     * @param account number used to deposit money.
     * @param amount to be deposited.
     * @param currency to be used in the deposit.
     * @return The result of the deposit in JSON format.
     */
    JSONObject depositMoney(String account, String amount, String currency);

    /**
     * Retrieves all available offers from the external system.
     * @return All available offers.
     */
    JSONArray getAvailableOffers();

    /**
     * Processes a ticket buying request.
     * @param accountId to be used in the purchase process.
     * @param amount to be purchased.
     * @param from Origin of the flight.
     * @param to Destination of the flight.
     * @return
     */
    JSONObject buyTicket(String accountId, String amount, String from, String to);

    /**
     * Clears all information previously processed
     */
    void reset();
}
