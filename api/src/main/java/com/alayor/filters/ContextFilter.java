package com.alayor.filters;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Filter that validates basic security information before executing Controllers.
 */
@Component
public class ContextFilter implements Filter
{
    public void init(FilterConfig filterConfig)
    {
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        initializeContext(httpServletRequest, httpServletResponse);
        chain.doFilter(request, response);
    }

    private void initializeContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        setHeaders(httpServletResponse, httpServletRequest);
    }

    private void setHeaders(HttpServletResponse response, HttpServletRequest request)
    {
        List<String> incomingURLs = singletonList("http://localhost:8080");
        String clientOrigin = request.getHeader("origin") != null ? request.getHeader("origin") : request.getHeader("referer");
        int myIndex = incomingURLs.indexOf(clientOrigin);
        if (myIndex != -1)
        {
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with,Authorization,Api-Key,User-Id,language");
            response.addHeader("Access-Control-Max-Age", "360");
            response.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT");
            response.addHeader("Access-Control-Allow-Origin", clientOrigin);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }
    }
}
