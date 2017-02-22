package com.alayor.repositories;

import com.alayor.api_service.model.entities.User;
import com.alayor.api_service.support.UserRepository;
import com.alayor.db.query_agent.DbBuilder;
import com.alayor.db.query_agent.QueryAgent;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

/**
 * Repository used to interact with the users table.
 */
@Component
public class UserMySQLRepository extends BaseRepository implements UserRepository
{
    public UserMySQLRepository()
    {
    }

    public UserMySQLRepository(QueryAgent queryAgent)
    {
        super(queryAgent);
    }

    /**
     * Insert a new user in the databse.
     * @param user to be inserted in repository.
     * @return the id of the new user.
     */
    public long insert(User user)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO users (name, email, password, active, api_key, is_admin, account_id) VALUES (");
        appendValuesForInsertion(user, sql);
        sql.append(")");
        try
        {
            return getQueryAgent().executeInsert(sql.toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not save user into database", e);
        }
    }

    private void appendValuesForInsertion(User user, StringBuilder sql)
    {
        sql.append("'").append(user.getName()).append("', ");
        sql.append("'").append(user.getEmail()).append("'").append(", ");
        sql.append("'").append(user.getPassword()).append("', ");
        sql.append(user.isActive()).append(", ");
        sql.append("'").append(user.getApiKey()).append("', ");
        sql.append(false).append(", ");
        sql.append("'").append(user.getAccountId()).append("'");
    }

    /**
     * Get a user by its email and password.
     * @param email used to retrieve the user.
     * @param password used to retrieve the user.
     * @return
     */
    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password)
    {
        try
        {
            return getQueryAgent().selectObject(new DbBuilder<User>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM users WHERE email = ? AND password = ?";
                }

                @Override public Object[] values()
                {
                    return new Object[] { email, password };
                }

                @Override public Optional<User> build(ResultSet resultSet) throws SQLException
                {
                    return of(buildUser(resultSet));
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get user from db", e);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException
    {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getBoolean("active"),
                resultSet.getString("api_key"),
                resultSet.getBoolean("is_admin"),
                resultSet.getString("account_id"));
    }

    /**
     * Get a user by its email.
     * @param email used to retrieve the user.
     * @return
     */
    @Override
    public Optional<User> getUserByEmail(String email)
    {
        try
        {
            return getQueryAgent().selectObject(new DbBuilder<User>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM users WHERE email = ?";
                }

                @Override public Object[] values()
                {
                    return new Object[] { email };
                }

                @Override public Optional<User> build(ResultSet resultSet) throws SQLException
                {
                    return of(buildUser(resultSet));
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get user from db", e);
        }
    }

    /**
     * Get a user by its id.
     * @param userId used to retrieve the user.
     * @return
     */
    @Override
    public Optional<User> getUserById(String userId)
    {
        try
        {
            return getQueryAgent().selectObject(new DbBuilder<User>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM users WHERE user_id = ?";
                }

                @Override public Object[] values()
                {
                    return new Object[] { userId };
                }

                @Override public Optional<User> build(ResultSet resultSet) throws SQLException
                {
                    return of(buildUser(resultSet));
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get user from db", e);
        }
    }

    /**
     * Get a user by its id and api key.
     * @param userId used to retrieve the user.
     * @param apiKey used to retrieve the user.
     * @return
     */
    @Override
    public Optional<User> getUserByIdAndApiKey(String userId, String apiKey)
    {
        try
        {
            return getQueryAgent().selectObject(new DbBuilder<User>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM users WHERE user_id = ? AND api_key = ?";
                }

                @Override public Object[] values()
                {
                    return new Object[] { userId, apiKey };
                }

                @Override public Optional<User> build(ResultSet resultSet) throws SQLException
                {
                    return of(buildUser(resultSet));
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get user from db", e);
        }
    }

    /**
     * Get all users in the database. (Except the admin user)
     * @return All the users. (Except the admin user)
     */
    @Override
    public List<Optional<User>> getAllUsers()
    {
        try
        {
            return getQueryAgent().selectList(new DbBuilder<User>()
            {
                @Override public String sql() throws SQLException
                {
                    return "SELECT * FROM users WHERE is_admin = false";
                }

                @Override public Object[] values()
                {
                    return new Object[0];
                }

                @Override public Optional<User> build(ResultSet resultSet) throws SQLException
                {
                    return of(buildUser(resultSet));
                }
            });
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get users from db", e);
        }
    }
}
