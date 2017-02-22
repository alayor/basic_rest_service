package com.alayor.security;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.responses.ServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest
{
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        authenticationService = new AuthenticationService(userService);
    }

    @Test
    public void shouldGetAdminUserFromService()
    {
        //given
        given(userService.getUserByIdAndApiKey(anyString(), anyString()))
                .willReturn(new ServiceResult<>(true, "", createUser(true)));

        //when
        authenticationService.isAdminUser("1", "1234");

        //then
        verify(userService).getUserByIdAndApiKey("1", "1234");
    }

    @Test
    public void shouldReturnTrueIfUserIsAdmin()
    {
        //given
        given(userService.getUserByIdAndApiKey(anyString(), anyString()))
                .willReturn(new ServiceResult<>(true, "", createUser(true)));

        //when
        boolean isAdminUser = authenticationService.isAdminUser("1", "1234");

        //then
        assertTrue(isAdminUser);
    }

    @Test
    public void shouldReturnFalseIfUserIsNoAdmin()
    {
        //given
        given(userService.getUserByIdAndApiKey(anyString(), anyString()))
                .willReturn(new ServiceResult<>(true, "", createUser(false)));

        //when
        boolean isAdminUser = authenticationService.isAdminUser("1", "1234");

        //then
        assertFalse(isAdminUser);
    }

    private User createUser(boolean isAdmin)
    {
        return new User(
                1L,
                "alayor",
                "alayor3@gmail.com",
                "secret",
                true,
                "1234",
                isAdmin,
                "1234");
    }
}
