package com.alayor.api_service.support;

import com.alayor.api_service.model.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Represents support to save and retrieve user information from some repository.
 */
public interface UserRepository
{
    /**
     * Represents support to save user information in the repository.
     * @param user to be inserted in repository.
     * @return id of the inserted user.
     */
    long insert(User user);

    /**
     * Represents support to retrieve a user by its email and password.
     * @param email used to retrieve the user.
     * @param password used to retrieve the user.
     * @return The user in case is found.
     */
    Optional<User> getUserByEmailAndPassword(String email, String password);

    /**
     * Represents support to retrieve a user by its email.
     * @param email used to retrieve the user.
     * @return The user in case is found.
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Represents support to retrieve a user by its id.
     * @param userId used to retrieve the user.
     * @return The user in case is found.
     */
    Optional<User> getUserById(String userId);

    /**
     * Represents support to retrieve a user by its id and api_key.
     * @param userId used to retrieve the user.
     * @param apiKey used to retrieve the user.
     * @return The user in case is found.
     */
    Optional<User> getUserByIdAndApiKey(String userId, String apiKey);

    /**
     * Represents support to be able to retrieve all the users from the repository.
     * @return All the users in the repository.
     */
    List<Optional<User>> getAllUsers();
}
