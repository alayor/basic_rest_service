package com.alayor.api_service;

import com.alayor.api_service.model.responses.PurchasedTicketWithUser;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.PurchasedTicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PurchasedTicketsServiceTest
{

    private PurchasedTicketsService purchasedTicketsService;
    @Mock
    private PurchasedTicketRepository purchasedTicketsRepository;
    @Mock
    private PurchasedTicketWithUser ticketWithUser;

    @Before
    public void setUp() throws Exception
    {
        purchasedTicketsService = new PurchasedTicketsService(purchasedTicketsRepository);
    }

    @Test
    public void shouldGetAllPurchasedTicketsWithUserFromRepository()
    {

        //when
        purchasedTicketsService.getAllPurchasedTicketsWithUsers();

        //then
        verify(purchasedTicketsRepository).getAllPurchasedTicketsByUser();
    }

    @Test
    public void shouldReturnPurchasedTicketsWithUser_IfRepositoryReturnsThem()
    {
        //given
        given(purchasedTicketsRepository.getAllPurchasedTicketsByUser()).willReturn(singletonList(of(ticketWithUser)));

        //when
        ServiceResult<List<PurchasedTicketWithUser>> serviceResult = purchasedTicketsService.getAllPurchasedTicketsWithUsers();

        //then
        assertEquals(1, serviceResult.getObject().size());
    }
}
