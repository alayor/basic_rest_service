package com.alayor.api_service;

import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.AirlineServiceClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@Component
public class OffersService
{
    private final AirlineServiceClient airlineServiceClient;

    @Autowired
    public OffersService(AirlineServiceClient airlineServiceClient)
    {
        this.airlineServiceClient = airlineServiceClient;
    }

    public ServiceResult<List<OfferRS>> getAvailableOffers()
    {
        List<OfferRS> availableOffers = new ArrayList<>();
        addOffersToList(availableOffers, airlineServiceClient.getAvailableOffers());
        return new ServiceResult<>(true, "", availableOffers);
    }

    private void addOffersToList(List<OfferRS> availableOffers, JSONArray offersArray)
    {
        for(int i = 0; i < offersArray.length(); i++)
        {
            JSONObject offer =  offersArray.getJSONObject(i);
            JSONObject route = offer.getJSONObject("route");
            JSONObject price = offer.getJSONObject("price");

            availableOffers.add(new OfferRS(
                    valueOf(price.getInt("amount")),
                    price.getString("currency"),
                    route.getString("from"),
                    route.getString("to"),
                    false
            ));
        }
    }
}
