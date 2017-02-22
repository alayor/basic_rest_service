package com.alayor.api.admin;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.entities.User;
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
public class UserListControllerTest
{
    private UserListController userListController;
    @Mock
    private UserService userService;
    @Mock
    private User user;

    @Before
    public void setUp() throws Exception
    {
        userListController = new UserListController(userService);
    }

    @Test
    public void shouldGetAllUsersFromService()
    {
        //when
        userListController.getAllUsers();

        //then
        verify(userService).getAllUsers();
    }

    @Test
    public void shouldReturnUsersIfServiceReturnThem()
    {
        //given
        given(userService.getAllUsers()).willReturn(new ServiceResult<>(true, "", singletonList(user)));

        //when
        ResponseEntity<ServiceResult<List<User>>> response = userListController.getAllUsers();

        //then
        assertEquals(1, response.getBody().getObject().size());
    }
}
