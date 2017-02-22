package com.alayor.api_service.model.requests;

import org.json.JSONObject;

public class UserRegistrationRQ
{
    private final String name;
    private final String email;
    private final String password;

    public UserRegistrationRQ(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRegistrationRQ(String json)
    {
        JSONObject jsonObject = new JSONObject(json);
        this.name = jsonObject.getString("name");
        this.email = jsonObject.getString("email");
        this.password =  jsonObject.getString("password");
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
}
