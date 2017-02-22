package com.alayor.filters;

import com.alayor.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * Filter that validates actions only intended for admin users before execute any Controller.
 */
@Component
public class SecurityFilter implements Filter {

    private final AuthenticationService authenticationService;

    @Autowired()
    public SecurityFilter(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!isValidCall(httpServletRequest)) {
            httpServletResponse.sendError(SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isValidCall(HttpServletRequest request)
    {
        return request.getMethod().equals("OPTIONS")
                || authenticationService.isAdminUser(request.getHeader("userId"), request.getHeader("password"));
    }
}
