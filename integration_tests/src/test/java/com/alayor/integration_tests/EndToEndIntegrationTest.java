package com.alayor.integration_tests;

import com.alayor.api_service.PurchaseService;
import com.alayor.api_service.UserService;
import com.alayor.api_service.model.requests.BuyTicketRQ;
import com.alayor.api_service.model.requests.UserLoginRQ;
import com.alayor.api_service.model.requests.UserRegistrationRQ;
import com.alayor.api_service.model.responses.LoginRS;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.PurchasedTicketRepository;
import com.alayor.api_service.support.UserRepository;
import com.alayor.db.datasources.DataSourceFactory;
import com.alayor.db.query_agent.QueryAgent;
import com.alayor.repositories.PurchasedTicketMySQLRepository;
import com.alayor.repositories.UserMySQLRepository;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class EndToEndIntegrationTest {
    private String userId;
    private UserService userService;
    private PurchaseService purchaseService;

    @Before
    public void setUp() throws Exception {
        initDependencies();
    }

    private void initDependencies() {
        DataSource dataSource = new DataSourceFactory().createDataSourceForTests();
        QueryAgent queryAgent = new QueryAgent(dataSource);
        UserRepository userRepository = new UserMySQLRepository(queryAgent);
        userService = new UserService(userRepository);
        PurchasedTicketRepository purchasedTicketRepository = new PurchasedTicketMySQLRepository(queryAgent);
        purchaseService = new PurchaseService(purchasedTicketRepository, userRepository);
    }

    @Test
    public void shouldCreateAccountAndBuyTicketsSuccessfully() {
        userService.registerUser(createUserRegistrationRQ());
        ServiceResult<LoginRS> serviceResult = userService.loginUser(createLoginRQ());
        userId = serviceResult.getObject().getUserId();
        ServiceResult<Optional> purchaseServiceResult = purchaseService.buyTicket(createBuyTicketRQ());
        assertTrue(purchaseServiceResult.isSuccess());
    }

    private UserRegistrationRQ createUserRegistrationRQ() {
        return new UserRegistrationRQ(
                "alayor",
                "alayor3@gmail.com",
                "secret"
        );
    }

    private BuyTicketRQ createBuyTicketRQ() {
        return new BuyTicketRQ(
                userId,
                "100",
                "USD",
                "London",
                "Madrid",
                "2"
        );
    }

    private UserLoginRQ createLoginRQ() {
        return new UserLoginRQ(
                "alayor3@gmail.com",
                "secret"
        );
    }

}
