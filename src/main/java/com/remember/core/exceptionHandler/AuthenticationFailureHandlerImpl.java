package com.remember.core.exceptionHandler;

import com.remember.core.exceptions.ErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        String redirectUrl = "/auth/forms/login?error=" + getMessage(exception);
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }

    private String getMessage(AuthenticationException exception) {
        if(exception instanceof BadCredentialsException)
            return ErrorCode.BAD_EMAIL_PASSWORD.name();
        return exception.getMessage();
    }
}
