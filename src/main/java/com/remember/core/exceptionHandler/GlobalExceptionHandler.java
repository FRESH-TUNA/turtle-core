package com.remember.core.exceptionHandler;

import com.remember.core.exceptionHandler.RedirectSupports.BindExceptionRedirectSupport;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    protected RedirectView handleMethodArgumentNotValidException(
            HttpServletRequest request,
            BindException e,
            RedirectAttributes attributes) {
        return BindExceptionRedirectSupport.process(request, e, attributes);
    }
}
