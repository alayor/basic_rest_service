package com.alayor.api_service;

import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.AirlineServiceClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OffersServiceTest
{

    private OffersService offerService;
    @Mock
    private AirlineServiceClient airlineServiceClient;

    @Before
    public void setUp() throws Exception
    {
        offerService = new OffersService(airlineServiceClient);
    }

    @Test
    public void shouldGetOffersFromAirlineService()
    {
        //given
        given(airlineServiceClient.getAvailableOffers()).willReturn(createArrayResponse());

        //when
        offerService.getAvailableOffers();

        //then
        verify(airlineServiceClient).getAvailableOffers();
    }

    private JSONArray createArrayResponse()
    {
        JSONArray array = new JSONArray();
        array.put(createOffer(100, "EUR", "London", "Madrid"));
        array.put(createOffer(200, "USD", "Madrid", "London"));
        return array;
    }

    @Test
    public void shouldReturnAvailableOffers_IfAirlineServiceReturnThem()
    {
        //given
        given(airlineServiceClient.getAvailableOffers()).willReturn(createArrayResponse());

        //when
        ServiceResult<List<OfferRS>> availableOffers = offerService.getAvailableOffers();

        //then
        assertEquals(2, availableOffers.getObject().size());
    }

    private JSONObject createOffer(int amount, String currency, String from, String to)
    {
        JSONObject offer1 = new JSONObject();
        offer1.put("route", createRoute(from, to));
        offer1.put("price", createPrice(amount, currency));
        return offer1;
    }

    private JSONObject createRoute(String from, String to)
    {
        JSONObject route = new JSONObject();
        route.put("from", from);
        route.put("to", to);
        return route;
    }

    private JSONObject createPrice(int amount, String currency)
    {
        JSONObject price = new JSONObject();
        price.put("amount", amount);
        price.put("currency", currency);
        return price;
    }
}
