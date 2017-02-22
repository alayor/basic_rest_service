package com.alayor.api.admin;

import com.alayor.api_service.PurchasedTicketsService;
import com.alayor.api_service.model.responses.PurchasedTicketWithUser;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PurchasedTicketsControllerTest
{
    private PurchasedTicketsController purchasedTicketsController;
    @Mock
    private PurchasedTicketsService purchasedTicketsService;
    @Mock
    private PurchasedTicketWithUser purchasedTicketWithUser;

    @Before
    public void setUp() throws Exception
    {
        purchasedTicketsController = new PurchasedTicketsController(purchasedTicketsService);
    }

    @Test
    public void shouldGetPurchasedTicketsFromService()
    {
        //when
        purchasedTicketsController.getAllPurchasedTicketsWithUsers();

        //then
        verify(purchasedTicketsService).getAllPurchasedTicketsWithUsers();
    }

    @Test
    public void shouldReturnUsersIfServiceReturnThem()
    {
        //given
        given(purchasedTicketsService.getAllPurchasedTicketsWithUsers())
                .willReturn(new ServiceResult<>(true, "", singletonList(purchasedTicketWithUser)));

        //when
        ResponseEntity<ServiceResult<List<PurchasedTicketWithUser>>> response = purchasedTicketsController.getAllPurchasedTicketsWithUsers();

        //then
        assertEquals(1, response.getBody().getObject().size());
    }
}
