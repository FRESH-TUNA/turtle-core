package com.remember.core.exceptionHandler;

import com.remember.core.exceptions.RememberAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws
            IOException {
        RememberAuthenticationException exceptionWrapper = (RememberAuthenticationException) exception;
        String redirectUrl = "/auth/forms/login?error=" + exceptionWrapper.getErrorCode().name();
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
