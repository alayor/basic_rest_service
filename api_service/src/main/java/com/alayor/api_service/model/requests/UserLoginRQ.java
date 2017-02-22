package com.alayor.api_service.model.requests;

import org.json.JSONObject;

public class UserLoginRQ
{
    private final String email;
    private final String password;

    public UserLoginRQ(String json)
    {
        JSONObject jsonObject = new JSONObject(json);
        this.email = jsonObject.getString("email");
        this.password =  jsonObject.getString("password");
    }

    public UserLoginRQ(String email, String password)
    {
        this.email = email;
        this.password = password;
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
