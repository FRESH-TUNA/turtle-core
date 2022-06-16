package com.remember.core.authentication.exceptionHandler;

import com.remember.core.exceptions.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String redirectUrl = "/auth/forms/login?error=" + ErrorCode.NOT_LOGINED;
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
