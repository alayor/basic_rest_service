package com.alayor.airline_service_client;

import com.alayor.api_service.support.AirlineServiceClient;
import com.alayor.tools.PropertyManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;

/**
 * Component used to send requests to external airline service
 * in order to retrieve information about accounts and tickets
 * as well as process ticket purchasing.
 */
@Component
public class AirlineServiceRequester implements AirlineServiceClient
{
    public JSONObject createNewAccount(String currency)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("currency", currency);
            HttpResponse<JsonNode> response =
                    createPostRequest(jsonObject, "/paypallets/account");
            return getJsonObject(response);
        }
        catch (UnirestException e)
        {
            throw new RuntimeException("There was an error when creating paypallets", e);
        }
    }

    private JSONObject getJsonObject(HttpResponse<JsonNode> jsonNodeHttpResponse)
    {
        return new JSONObject(jsonNodeHttpResponse.getBody().toString());
    }

    private HttpResponse<JsonNode> createPostRequest(JSONObject jsonObject, String path) throws UnirestException
    {
        PropertyManager propertyManager = new PropertyManager("airline_service_client.properties");
        return post(propertyManager.getProperty("api_url") + path)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(jsonObject)
                .asJson();
    }

    @Override
    public JSONObject depositMoney(String accountId, String amount, String currency)
    {
        try
        {
            JSONObject accountIdJson = new JSONObject();
            accountIdJson.put("accountId", accountId);
            accountIdJson.put("monetaryAmount", createMonetaryAmount(amount, currency));
            HttpResponse<JsonNode> response =
                    createPostRequest(accountIdJson, "/paypallets/account/deposit");
            if (response.getStatus() == 500)
            {
                throw new RuntimeException("External service API Forest is not working.");
            }
            return getJsonObject(response);
        }
        catch (UnirestException e)
        {
            throw new RuntimeException("There was an error when creating paypallets", e);
        }
    }

    @Override
    public JSONArray getAvailableOffers()
    {
        try
        {
            HttpResponse<JsonNode> response = createGetRequest("/gammaairlines/offers");
            if (response.getStatus() == 500)
            {
                throw new RuntimeException("External service API Forest is not working.");
            }
            return getJsonArray(response);
        }
        catch (UnirestException e)
        {
            throw new RuntimeException("There was an error when creating paypallets", e);
        }
    }

    @Override public JSONObject buyTicket(String accountId, String amount, String from, String to)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accountId", accountId);
            jsonObject.put("amount", amount);
            jsonObject.put("route", createRoute(from, to));
            HttpResponse<JsonNode> response =
                    createPostRequest(jsonObject, "/gammaairlines/tickets/buy");
            return getJsonObject(response);
        }
        catch (UnirestException e)
        {
            throw new RuntimeException("There was an error when creating paypallets", e);
        }
    }

    @Override public void reset()
    {
        try
        {
            HttpResponse<JsonNode> response = createGetRequest("/reset");
            if (response.getStatus() == 500)
            {
                throw new RuntimeException("External service API Forest is not working.");
            }
        }
        catch (UnirestException e)
        {
            throw new RuntimeException("There was an error when creating paypallets", e);
        }
    }

    private JSONObject createRoute(String from, String to)
    {
        JSONObject route = new JSONObject();
        route.put("from", from);
        route.put("to", to);
        return route;
    }

    private HttpResponse<JsonNode> createGetRequest(String path) throws UnirestException
    {
        PropertyManager propertyManager = new PropertyManager("airline_service_client.properties");
        return get(propertyManager.getProperty("api_url") + path)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asJson();
    }

    private JSONArray getJsonArray(HttpResponse<JsonNode> jsonNodeHttpResponse)
    {
        return new JSONArray(jsonNodeHttpResponse.getBody().toString());
    }

    private JSONObject createMonetaryAmount(String amount, String currency)
    {
        JSONObject monetaryAmountJson = new JSONObject();
        monetaryAmountJson.put("amount", amount);
        monetaryAmountJson.put("currency", currency);
        return monetaryAmountJson;
    }
}
