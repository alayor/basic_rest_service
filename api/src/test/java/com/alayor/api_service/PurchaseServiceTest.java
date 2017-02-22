package com.alayor.api_service;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.support.AirlineServiceClient;
import com.alayor.api_service.support.PurchasedTicketRepository;
import com.alayor.api_service.support.UserRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest
{

    private PurchaseService purchaseService;
    @Mock
    private PurchasedTicketRepository purchasedTicketRepository;
    @Mock
    private AirlineServiceClient airlineServiceClient;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    @Before
    public void setUp() throws Exception
    {
        purchaseService = new PurchaseService(purchasedTicketRepository, airlineServiceClient, userRepository);
    }

    @Test
    public void shouldSavePurchasedTicketIntoDb()
    {
        //given
        given(airlineServiceClient.buyTicket(anyString(), anyString(), anyString(), anyString())).willReturn(new JSONObject("{amount: 100}"));
        given(purchasedTicketRepository.insert(any(PurchasedTicket.class))).willReturn(1L);
        given(userRepository.getUserById(anyString())).willReturn(of(user));

        //when
        purchaseService.buyTicket(createBuyTicket());

        //then
        verify(purchasedTicketRepository).insert(any(PurchasedTicket.class));
    }

    @Test
    public void shouldThrowExceptionIfBoughtTicketHasNoAmount()
    {
        //given
        given(airlineServiceClient.buyTicket(anyString(), anyString(), anyString(), anyString())).willReturn(new JSONObject("{}"));
        given(purchasedTicketRepository.insert(any(PurchasedTicket.class))).willReturn(1L);
        given(userRepository.getUserById(anyString())).willReturn(of(user));

        //when
        try
        {
            purchaseService.buyTicket(createBuyTicket());
        }

        //then
        catch (Exception ex)
        {
            assertEquals("Ticket was not purchased successfully. Please contact our support department.", ex.getMessage());
        }
    }

    @Test
    public void shouldSendBuyRequestToAirlineService()
    {
        //given
        given(airlineServiceClient.buyTicket(anyString(), anyString(), anyString(), anyString())).willReturn(new JSONObject("{amount: 100}"));
        given(purchasedTicketRepository.insert(any(PurchasedTicket.class))).willReturn(1L);
        given(userRepository.getUserById(anyString())).willReturn(of(user));
        given(user.getAccountId()).willReturn("1234");

        //when
        purchaseService.buyTicket(createBuyTicket());

        //then
        verify(airlineServiceClient).buyTicket("1234", "2", "London", "Madrid");
    }

    @Test
    public void shouldThrowExceptionIfPurchasedTicketCouldNotBeSaved()
    {
        //given
        given(airlineServiceClient.buyTicket(anyString(), anyString(), anyString(), anyString())).willReturn(new JSONObject("{amount: 100}"));
        given(userRepository.getUserById(anyString())).willReturn(of(user));
        given(user.getAccountId()).willReturn("1234");
        given(purchasedTicketRepository.insert(any(PurchasedTicket.class))).willReturn(0L);

        //when
        try
        {
            purchaseService.buyTicket(createBuyTicket());
        }

        //then
        catch (Exception ex)
        {
            assertEquals("Ticket was not purchased successfully. Please contact our support department.", ex.getMessage());
        }
    }

    private BuyTicketRQ createBuyTicket()
    {
        return new BuyTicketRQ("1", "1000", "USD", "London", "Madrid", "2");
    }
}
