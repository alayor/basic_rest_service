package com.alayor.api_service.user;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.UserLoginRQ;
import com.alayor.api_service.model.requests.UserRegistrationRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(userRepository);
        given(userRepository.getUserByEmail(anyString())).willReturn(empty());
    }

    @Test
    public void shouldRegisterUserSuccessfully() {
        //given
        UserRegistrationRQ userRegistrationRQ = createUserRegistration();
        given(userRepository.insert(any(User.class))).willReturn(1L);

        //when
        userService.registerUser(userRegistrationRQ);

        //then
        verify(userRepository).insert(any(User.class));
    }

    @Test
    public void shouldReturnErrorIfEmailAlreadyExists() {
        //given
        UserRegistrationRQ userRegistrationRQ = createUserRegistration();
        given(userRepository.getUserByEmail(anyString())).willReturn(of(user));
        given(userRepository.insert(any(User.class))).willReturn(1L);

        //when
        ServiceResult serviceResult = userService.registerUser(userRegistrationRQ);

        //then
        assertFalse(serviceResult.isSuccess());
        assertEquals("This email has already been used", serviceResult.getMessage());
    }

    private UserRegistrationRQ createUserRegistration() {
        return new UserRegistrationRQ(
                "alayor",
                "alayor3@gmail.com",
                "password"
        );
    }

    @Test
    public void shouldGetUserFromRepository() {
        //given
        UserLoginRQ userLoginRQ = new UserLoginRQ("alayor3@gmail.com", "password");
        given(userRepository.getUserByEmailAndPassword(anyString(), anyString())).willReturn(of(user));

        //when
        userService.loginUser(userLoginRQ);

        //then
        verify(userRepository).getUserByEmailAndPassword("alayor3@gmail.com", "password");
    }

    @Test
    public void shouldReturnSuccessIfUserWasFoundInDB() {
        //given
        UserLoginRQ userLoginRQ = new UserLoginRQ("alayor3@gmail.com", "password");
        given(userRepository.getUserByEmailAndPassword(anyString(), anyString())).willReturn(of(user));

        //when
        ServiceResult serviceResult = userService.loginUser(userLoginRQ);

        //then
        assertTrue(serviceResult.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessage_IfUserWasNotFoundInDB() {
        //given
        UserLoginRQ userLoginRQ = new UserLoginRQ("alayor3@gmail.com", "password");
        given(userRepository.getUserByEmailAndPassword(anyString(), anyString())).willReturn(empty());

        //when
        ServiceResult serviceResult = userService.loginUser(userLoginRQ);

        //then
        assertFalse(serviceResult.isSuccess());
        assertEquals("Invalid credentials.", serviceResult.getMessage());
    }

    @Test
    public void shouldGetUserByIdAndApiKeyFromRepository() {
        //given
        given(userRepository.getUserByIdAndApiKey(anyString(), anyString())).willReturn(of(user));

        //when
        userService.getUserByIdAndApiKey("1", "1234");

        //then
        verify(userRepository).getUserByIdAndApiKey("1", "1234");
    }

    @Test
    public void shouldReturnUserIfFound() {
        //given
        given(userRepository.getUserByIdAndApiKey(anyString(), anyString())).willReturn(of(user));

        //when
        ServiceResult<User> serviceResult = userService.getUserByIdAndApiKey("1", "1234");

        //then
        assertTrue(serviceResult.isSuccess());
        assertEquals(user, serviceResult.getObject());
    }

    @Test
    public void shouldReturnFalseIfNotFound() {
        //given
        given(userRepository.getUserByIdAndApiKey(anyString(), anyString())).willReturn(empty());

        //when
        ServiceResult<User> serviceResult = userService.getUserByIdAndApiKey("1", "1234");

        //then
        assertFalse(serviceResult.isSuccess());
    }

    @Test
    public void shouldRetrieveAllUsersFromRepository() {
        //given
        given(userRepository.getAllUsers()).willReturn(singletonList(of(user)));

        //when
        ServiceResult<List<User>> users = userService.getAllUsers();

        //then
        verify(userRepository).getAllUsers();
        assertEquals(1, users.getObject().size());
    }
}
