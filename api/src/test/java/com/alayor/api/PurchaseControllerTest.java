package com.alayor.api;

import com.alayor.api_service.PurchaseService;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseControllerTest
{

    private PurchaseController purchaseController;
    @Mock
    private PurchaseService purchaseService;

    @Before
    public void setUp() throws Exception
    {
        purchaseController = new PurchaseController(purchaseService);
    }

    @Test
    public void shouldCallPurchaseService()
    {
        //given
        String buyTicket = createBuyTicket();

        //when
        purchaseController.buyTicket(new HttpEntity<>(buyTicket));

        //then
        verify(purchaseService).buyTicket(any(BuyTicketRQ.class));
    }

    @Test
    public void shouldReturnMessageFromService()
    {
        //given
        given(purchaseService.buyTicket(any(BuyTicketRQ.class))).willReturn(
                new ServiceResult<>(true, "Ticket was bought successfully", empty()));

        //when
        ResponseEntity<ServiceResult> response = purchaseController.buyTicket(new HttpEntity<>(createBuyTicket()));

        //then
        assertEquals("Ticket was bought successfully", response.getBody().getMessage());
    }

    private String createBuyTicket()
    {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "1");
        map.put("amount", "1000");
        map.put("currency", "USD");
        map.put("from", "DFW");
        map.put("to", "MEX");
        map.put("quantity", "1");
        return new JSONObject(map).toJSONString();
    }

    @Test
    public void shouldReturnNonSuccessfulResult_IfPurchaseServiceThrowsException()
    {
        //given
        given(purchaseService.buyTicket(any(BuyTicketRQ.class))).willThrow(new RuntimeException());

        //when
        ResponseEntity<ServiceResult> responseEntity = purchaseController.buyTicket(new HttpEntity<>(createBuyTicket()));

        //then
        assertFalse(responseEntity.getBody().isSuccess());
    }
}
