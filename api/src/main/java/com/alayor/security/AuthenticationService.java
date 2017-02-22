package com.alayor.security;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verifies if the user sending a request is an admin user.
 */
@Component
public class AuthenticationService
{
    private final UserService userService;

    @Autowired
    public AuthenticationService(UserService userService)
    {
        this.userService = userService;
    }

    public boolean isAdminUser(String userId, String apiKey)
    {
        ServiceResult<User> serviceResult = userService.getUserByIdAndApiKey(userId, apiKey);
        return serviceResult.getObject().isAdmin();
    }
}
