package com.alayor.api_service.model.entities;

public class User
{
    private final Long userId;
    private final String name;
    private final String email;
    private final String password;
    private final boolean active;
    private final String apiKey;
    private final boolean isAdmin;
    private final String accountId;

    public User(Long userId, String name, String email, String password, boolean active, String apiKey, boolean isAdmin, String accountId)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.apiKey = apiKey;
        this.isAdmin = isAdmin;
        this.accountId = accountId;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isActive()
    {
        return active;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public Long getUserId()
    {
        return userId;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public String getAccountId()
    {
        return accountId;
    }
}
