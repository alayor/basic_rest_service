package com.alayor.api;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.requests.UserLoginRQ;
import com.alayor.api_service.model.requests.UserRegistrationRQ;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Controller used to register and login users
 */
@RestController
@RequestMapping("/users")
public class UserController
{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Registers a user with the provided information in entity
     * @param entity Contains the user information
     * @return Success or failure in case the user was not successful registered.
     */
    @RequestMapping(value = "/register", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult> registerUser(HttpEntity<String> entity)
    {
        try
        {
            UserRegistrationRQ userRegistrationRQ = new UserRegistrationRQ(entity.getBody());
            ServiceResult serviceResult = userService.registerUser(userRegistrationRQ);
            return new ResponseEntity<>(serviceResult, OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ServiceResult(false), INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Process a user login with the provided information.
     * @param entity Contains email and password to process the login
     * @return Success or failure in case the user was not able to be logged in.
     */
    @RequestMapping(value = "/login", method = POST)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult> loginUser(HttpEntity<String> entity)
    {
        try
        {
            UserLoginRQ userLoginRQ = new UserLoginRQ(entity.getBody());
            ServiceResult serviceResult = userService.loginUser(userLoginRQ);
            return new ResponseEntity<>(serviceResult, OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(new ServiceResult(false), INTERNAL_SERVER_ERROR);
        }
    }
}
