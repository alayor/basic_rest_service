package com.alayor.api_service;

import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.SendEmailRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.mail.MessagingException;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest
{

    private NotificationService notificationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    @Before
    public void setUp() throws Exception
    {
        notificationService = new NotificationService(userRepository) {
            @Override
            void sendEmail(String to, String body) throws MessagingException
            {
                //Do nothing
            }
        };
    }

    @Test
    public void shouldGetEmailFromUserRepository()
    {
        //given
        given(userRepository.getUserById(anyString())).willReturn(Optional.of(user));

        //when
        notificationService.sendEmailToUser(createSendEmailRQ());

        //then
        verify(userRepository).getUserById("1");
    }

    @Test
    public void shouldReturnErrorIfUserWasNotFound()
    {
        //given
        given(userRepository.getUserById(anyString())).willReturn(empty());

        //when
        ServiceResult<Optional> serviceResult = notificationService.sendEmailToUser(createSendEmailRQ());

        //then
        assertFalse(serviceResult.isSuccess());
        assertEquals("An error occurred and email was not sent :(", serviceResult.getMessage());
    }

    private SendEmailRQ createSendEmailRQ()
    {
        return new SendEmailRQ(
                "1",
                "1000",
                "USD",
                "DFW",
                "MEX",
                "2"
        );
    }
}
