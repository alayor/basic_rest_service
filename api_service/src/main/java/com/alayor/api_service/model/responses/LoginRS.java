package com.alayor.api_service.model.responses;

public class LoginRS
{

    private final String userId;
    private final boolean isAdmin;
    private final String apiKey;

    public LoginRS(String userId, boolean isAdmin, String apiKey)
    {
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.apiKey = apiKey;
    }

    public String getUserId()
    {
        return userId;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public String getApiKey()
    {
        return apiKey;
    }
}
