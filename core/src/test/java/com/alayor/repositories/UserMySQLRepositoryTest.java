package com.alayor.repositories;

import com.alayor.api_service.model.entities.User;
import com.alayor.db.query_agent.DbBuilder;
import com.alayor.db.query_agent.QueryAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserMySQLRepositoryTest
{
    private UserMySQLRepository repository;
    @Mock
    private QueryAgent queryAgent;

    @Before
    public void setup() throws Exception
    {
        repository = new UserMySQLRepository(queryAgent);
    }

    @Test
    public void shouldCallExecuteInsert() throws Exception
    {
        //given
        User user = new User(
                0L,
                "alayor",
                "alayor3@gmail.com",
                "password",
                false,
                "api_key",
                false,
                "1234");

        //when
        repository.insert(user);

        //then
        verify(queryAgent).executeInsert("INSERT INTO users (name, email, password, active, api_key, is_admin, account_id) "
                + "VALUES ('alayor', 'alayor3@gmail.com', 'password', false, 'api_key', false, '1234')");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSelectObjectByEmailAndPassword() throws Exception
    {
        //when
        repository.getUserByEmailAndPassword("alayor3@gmail.com", "secret");

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectObject(argument.capture());
        assertEquals("SELECT * FROM users WHERE email = ? AND password = ?", argument.getValue().sql());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSelectObjectByEmail() throws Exception
    {
        //when
        repository.getUserByEmail("alayor3@gmail.com");

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectObject(argument.capture());
        assertEquals("SELECT * FROM users WHERE email = ?", argument.getValue().sql());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSelectObjectByUserId() throws Exception
    {
        //when
        repository.getUserById("1");

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectObject(argument.capture());
        assertEquals("SELECT * FROM users WHERE user_id = ?", argument.getValue().sql());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSelectObjectByUserIdAndApiKey() throws Exception
    {
        //when
        repository.getUserByIdAndApiKey("1", "1234");

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectObject(argument.capture());
        assertEquals("SELECT * FROM users WHERE user_id = ? AND api_key = ?", argument.getValue().sql());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldSelectAllUsers() throws Exception
    {
        //when
        repository.getAllUsers();

        //then
        ArgumentCaptor<DbBuilder> argument = forClass(DbBuilder.class);
        verify(queryAgent).selectList(argument.capture());
        assertEquals("SELECT * FROM users WHERE is_admin = false", argument.getValue().sql());
    }
}
