package com.alayor.repositories;

import com.alayor.api_service.model.entities.PurchasedTicket;
import com.alayor.db.query_agent.DbBuilder;
import com.alayor.db.query_agent.QueryAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PurchasedTicketMySQLRepositoryTest
{
    private PurchasedTicketMySQLRepository repository;
    @Mock
    private QueryAgent queryAgent;

    @Before
    public void setup() throws Exception
    {
        repository = new PurchasedTicketMySQLRepository(queryAgent);
    }

    @Test
    public void shouldCallExecuteInsert() throws Exception
    {
        //given
        PurchasedTicket purchasedTicket = new PurchasedTicket(
                "1",
                "1000",
                "USD",
                "DFW",
                "MEX"
        );
        //when
        repository.insert(purchasedTicket);

        //then
        verify(queryAgent).executeInsert("INSERT INTO purchased_tickets (user_id, amount, currency, origin, destination) "
                + "VALUES (1, '1000', 'USD', 'DFW', 'MEX')");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldRetrievePurchasedTicketsWithUsers() throws Exception
    {
        //when
        repository.getAllPurchasedTicketsByUser();

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectList(argument.capture());
        assertEquals("SELECT * FROM purchased_tickets INNER JOIN users USING(user_id)", argument.getValue().sql());
    }
}
