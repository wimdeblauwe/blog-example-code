package com.wimdeblauwe.examples.thymeleafhtmxautherrorhandling;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HxRefreshHeaderAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint forbiddenEntryPoint;

    public HxRefreshHeaderAuthenticationEntryPoint() {
        this.forbiddenEntryPoint = new Http403ForbiddenEntryPoint();
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("HX-Refresh", "true");
        forbiddenEntryPoint.commence(request, response, authException);
    }
}
