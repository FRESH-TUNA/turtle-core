package com.remember.core.exceptionHandler;

import com.remember.core.exceptions.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RememberAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException exception) throws IOException, ServletException {
        if (exception instanceof MissingCsrfTokenException) {
            String redirectUrl = "/auth/forms/login?error=" + ErrorCode.NOT_LOGINED;
            response.sendRedirect(request.getContextPath() + redirectUrl);
        } else if (exception instanceof InvalidCsrfTokenException) {
            String redirectUrl = "/auth/forms/login?error=" + ErrorCode.NOT_LOGINED;
            response.sendRedirect(request.getContextPath() + redirectUrl);
        }
    }
}
