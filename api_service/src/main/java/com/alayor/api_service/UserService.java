package com.alayor.api_service;

import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.model.requests.UserLoginRQ;
import com.alayor.api_service.model.requests.UserRegistrationRQ;
import com.alayor.api_service.model.responses.LoginRS;
import com.alayor.api_service.model.responses.ServiceResult;
import com.alayor.api_service.support.AirlineServiceClient;
import com.alayor.api_service.support.UserRepository;
import com.alayor.tools.PropertyManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.RandomStringUtils.random;

/**
 * Service used to register and login users to the system as well
 * as create accounts and deposit money.
 */
@Component
public class UserService
{
    private final PropertyManager propertyManager = new PropertyManager("api_service.properties");
    private final UserRepository userRepository;
    private final AirlineServiceClient airlineServiceClient;

    @Autowired
    public UserService(UserRepository userRepository, AirlineServiceClient airlineServiceClient)
    {
        this.userRepository = userRepository;
        this.airlineServiceClient = airlineServiceClient;
    }

    /**
     * Registers an user in the system. Creates an account and deposits money to it using
     * the external system.
     * @param userRegistrationRQ Contains the information for the registration.
     * @return Success or failure given the case.
     */
    public ServiceResult<Optional> registerUser(UserRegistrationRQ userRegistrationRQ)
    {

        String accountId = createAccountAndMakeDeposit();
        ServiceResult<Optional> serviceResult = saveUserInDb(userRegistrationRQ, accountId);
        if (!serviceResult.isSuccess())
        {
            return serviceResult;
        }
        return new ServiceResult<>(true, "Your account was successfully created", empty());
    }

    private ServiceResult<Optional> saveUserInDb(UserRegistrationRQ userRegistrationRQ, String accountId)
    {
        if (userRepository.getUserByEmail(userRegistrationRQ.getEmail()).isPresent())
        {
            return new ServiceResult<>(false, "This email has already been used", empty());
        }
        long insert = userRepository.insert(createUser(userRegistrationRQ, accountId));
        if (insert <= 0)
        {
            throw new RuntimeException("User was not inserted in db");
        }
        return new ServiceResult<>();
    }

    private User createUser(UserRegistrationRQ userRegistrationRQ, String accountId)
    {
        return new User(
                0L,
                userRegistrationRQ.getName(),
                userRegistrationRQ.getEmail(),
                userRegistrationRQ.getPassword(),
                true,
                generateApiKey(),
                false,
                accountId);
    }

    private String createAccountAndMakeDeposit()
    {
        JSONObject createAccountResponse = createAccount();
        makeDeposit(createAccountResponse);
        return createAccountResponse.get("id").toString();
    }

    private JSONObject createAccount()
    {
        JSONObject createAccountResponse = airlineServiceClient.createNewAccount("USD");
        if (!createAccountResponse.has("id"))
        {
            throw new RuntimeException("External service API Forest is not working.");
        }
        return createAccountResponse;
    }

    private void makeDeposit(JSONObject createAccountResponse)
    {
        JSONObject depositResponse = airlineServiceClient.depositMoney(
                createAccountResponse.get("id").toString(),
                propertyManager.getProperty("registration.initial.deposit.amount"),
                propertyManager.getProperty("registration.initial.deposit.currency"));
        if (!depositResponse.has("id"))
        {
            throw new RuntimeException("External service API Forest is not working.");
        }
    }

    private String generateApiKey() {
        return random(60, true, true);
    }

    /**
     * Logs in a user in the system.
     * @param userLoginRQ Contains email and password.
     * @return Success or failure given the case.
     */
    public ServiceResult<LoginRS> loginUser(UserLoginRQ userLoginRQ)
    {
        Optional<User> userByEmailAndPassword =
                userRepository.getUserByEmailAndPassword(userLoginRQ.getEmail(), userLoginRQ.getPassword());
        if (!userByEmailAndPassword.isPresent())
        {
            return new ServiceResult<>(false, "Invalid credentials.", null);
        }
        return new ServiceResult<>(true, "", createLoginRS(userByEmailAndPassword.get()));
    }

    private LoginRS createLoginRS(User userByEmailAndPassword)
    {
        return new LoginRS(
                userByEmailAndPassword.getUserId().toString(),
                userByEmailAndPassword.isAdmin(),
                userByEmailAndPassword.getApiKey());
    }

    /**
     * Retrieves a user using its id and api key.
     * @param userId used to retrieve the user.
     * @param apiKey used to retrieve the user.
     * @return the users if it was found.
     */
    public ServiceResult<User> getUserByIdAndApiKey(String userId, String apiKey)
    {
        Optional<User> user = userRepository.getUserByIdAndApiKey(userId, apiKey);
        if (user.isPresent())
        {
            return new ServiceResult<>(true, "", user.get());
        }
        return new ServiceResult<>(false, "", null);
    }

    /**
     * Retrieves all the users registered in the system.
     * @return All the users in the system.
     */
    public ServiceResult<List<User>> getAllUsers()
    {
        List<Optional<User>> optionalUsers = userRepository.getAllUsers();
        List<User> users = optionalUsers.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        return new ServiceResult<>(true, "", users);
    }

}
