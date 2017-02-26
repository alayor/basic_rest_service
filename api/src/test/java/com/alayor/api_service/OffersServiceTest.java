package com.alayor.api_service;

import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OffersServiceTest {

    private OffersService offerService;

    @Before
    public void setUp() throws Exception {
        offerService = new OffersService();
    }

    @Test
    public void shouldReturnAvailableOffers_IfAirlineServiceReturnThem() {
        //when
        ServiceResult<List<OfferRS>> availableOffers = offerService.getAvailableOffers();

        //then
        assertEquals(2, availableOffers.getObject().size());
    }
}
