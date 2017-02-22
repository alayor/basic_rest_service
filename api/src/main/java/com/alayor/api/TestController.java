package com.alayor.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller used only to check if the system is responding.
 */
@RestController
@RequestMapping("/test")
public class TestController
{
    @RequestMapping(method = GET)
    public String test()
    {
        return "OK";
    }
}
