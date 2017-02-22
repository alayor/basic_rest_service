package com.alayor.api_service.model.responses;

public class ServiceResult<T>
{
    private final boolean success;
    private String message;
    private T object;

    public ServiceResult()
    {
        success = true;
    }

    public ServiceResult(boolean success)
    {
        this.success = success;
    }

    public ServiceResult(boolean success, String message, T object)
    {
        this.success = success;
        this.message = message;
        this.object = object;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

    public T getObject()
    {
        return object;
    }
}
