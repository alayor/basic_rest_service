package com.alayor.api;

import com.alayor.api_service.OffersService;
import com.alayor.api_service.model.responses.OfferRS;
import com.alayor.api_service.model.responses.ServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OffersControllerTest
{
    private OffersController offersController;
    @Mock
    private OffersService offersService;

    @Before
    public void setUp() throws Exception
    {
        offersController = new OffersController(offersService);
    }

    @Test
    public void shouldGetAvailableOffersFromService()
    {
        //when
        offersController.getAvailableOffers();

        //then
        verify(offersService).getAvailableOffers();
    }

    @Test
    public void shouldReturnAvailableOffers_IfServiceReturnsOffers()
    {
        //given
        OfferRS offerRS = new OfferRS("1000", "USD", "DFW", "MEX", false);
        given(offersService.getAvailableOffers()).willReturn(new ServiceResult<>(true, "", singletonList(offerRS)));

        //when
        ResponseEntity<ServiceResult<List<OfferRS>>> response = offersController.getAvailableOffers();

        //then
        List<OfferRS> offerRSS = response.getBody().getObject();
        assertEquals(1, offerRSS.size());
        assertEquals("1000", offerRSS.get(0).getAmount());
        assertEquals("USD", offerRSS.get(0).getCurrency());
        assertEquals("DFW", offerRSS.get(0).getFrom());
        assertEquals("MEX", offerRSS.get(0).getTo());
    }

    @Test
    public void shouldReturnNonSuccessfulResult_IfOffersServiceThrowsException()
    {
        //given
        given(offersService.getAvailableOffers()).willThrow(new RuntimeException());

        //when
        ResponseEntity<ServiceResult<List<OfferRS>>> responseEntity = offersController.getAvailableOffers();

        //then
        assertFalse(responseEntity.getBody().isSuccess());
    }
}
