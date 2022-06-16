package com.remember.core.exceptionHandler;

import com.remember.core.exceptionHandler.RedirectSupports.BindExceptionRedirectSupport;
import com.remember.core.exceptionHandler.RedirectSupports.RememberAuthenticatedExceptionRedirectSupport;
import com.remember.core.exceptionHandler.RedirectSupports.RememberBusinessExceptionRedirectSupport;
import com.remember.core.exceptions.RememberAuthenticationException;
import com.remember.core.exceptions.RememberBusinessException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    protected RedirectView handleBindException(
            HttpServletRequest request,
            BindException e,
            RedirectAttributes attributes) {
        return BindExceptionRedirectSupport.process(request, e, attributes);
    }

    @ExceptionHandler(RememberAuthenticationException.class)
    protected RedirectView handleRememberAuthenticationException(
            HttpServletRequest request,
            RememberAuthenticationException e,
            RedirectAttributes attributes) {
        return RememberAuthenticatedExceptionRedirectSupport.process(request, e, attributes);
    }

    @ExceptionHandler(RememberBusinessException.class)
    protected RedirectView handleRememberBusinessException(
            HttpServletRequest request,
            RememberBusinessException e,
            RedirectAttributes attributes) {
        return RememberBusinessExceptionRedirectSupport.process(request, e, attributes);
    }
}
