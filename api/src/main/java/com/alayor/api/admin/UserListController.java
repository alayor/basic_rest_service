package com.alayor.api.admin;

import com.alayor.api_service.UserService;
import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.responses.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller used by admin users to get all registered users
 */
@RestController
@RequestMapping("/admin/users")
public class UserListController
{
    private final UserService userService;

    @Autowired
    public UserListController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Retrieves all the users registered in the system.
     * @return All registered users
     */
    @RequestMapping(value = "", method = GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ServiceResult<List<User>>> getAllUsers()
    {
        ServiceResult<List<User>> serviceResult = userService.getAllUsers();
        return new ResponseEntity<>(serviceResult, OK);
    }
}
