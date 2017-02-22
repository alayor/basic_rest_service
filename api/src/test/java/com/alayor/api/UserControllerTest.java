package com.alayor.api;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.requests.UserLoginRQ;
import com.alayor.api_service.model.requests.UserRegistrationRQ;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest
{
    private UserController userController;
    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        userController = new UserController(userService);
    }

    @Test
    public void shouldCallRegisterUserInService()
    {
        //given
        String userRegistrationJson = createUserRegistration();

        //when
        userController.registerUser(new HttpEntity<>(userRegistrationJson));

        //then
        verify(userService).registerUser(any(UserRegistrationRQ.class));
    }

    private String createUserRegistration()
    {
        Map<String, String> map = new HashMap<>();
        map.put("name", "alayor");
        map.put("email", "alayor3@gmail.com");
        map.put("password", "password");
        map.put("currency", "USD");
        return new JSONObject(map).toJSONString();
    }

    @Test
    public void shouldReturnSuccessfulResult_IfUserServiceResultIsSuccessful()
    {
        //given
        given(userService.registerUser(any(UserRegistrationRQ.class))).willReturn(new ServiceResult());
        String userRegistrationJson = createUserRegistration();

        //when
        ResponseEntity<ServiceResult> responseEntity = userController.registerUser(new HttpEntity<>(userRegistrationJson));

        //then
        assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    public void shouldReturnNonSuccessfulResult_IfUserServiceThrowsException()
    {
        //given
        given(userService.registerUser(any(UserRegistrationRQ.class))).willThrow(new RuntimeException());
        String userRegistrationJson = createUserRegistration();

        //when
        ResponseEntity<ServiceResult> responseEntity = userController.registerUser(new HttpEntity<>(userRegistrationJson));

        //then
        assertFalse(responseEntity.getBody().isSuccess());
    }

    @Test
    public void shouldCallLoginService()
    {
        //given
        String userLoginJson = createUserLogin();

        //when
        userController.loginUser(new HttpEntity<>(userLoginJson));

        //then
        verify(userService).loginUser(any(UserLoginRQ.class));
    }

    @Test
    public void shouldReturnSuccess_IfLoginIsSuccessful()
    {
        //given
        given(userService.loginUser(any(UserLoginRQ.class))).willReturn(new ServiceResult());
        String userLoginJson = createUserLogin();

        //when
        ResponseEntity<ServiceResult> responseEntity = userController.loginUser(new HttpEntity<>(userLoginJson));

        //then
        assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    public void shouldReturnNoSuccess_IfLoginThrowsException()
    {
        //given
        given(userService.loginUser(any(UserLoginRQ.class))).willThrow(new RuntimeException());
        String userLoginJson = createUserLogin();

        //when
        ResponseEntity<ServiceResult> responseEntity = userController.loginUser(new HttpEntity<>(userLoginJson));

        //then
        assertFalse(responseEntity.getBody().isSuccess());
    }

    private String createUserLogin()
    {
        Map<String, String> map = new HashMap<>();
        map.put("email", "alayor3@gmail.com");
        map.put("password", "password");
        return new JSONObject(map).toJSONString();
    }
}
