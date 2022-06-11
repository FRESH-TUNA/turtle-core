package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.ErrorResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;


public class BindExceptionRedirectSupport {
    public static RedirectView process(HttpServletRequest request,
                                       BindException e,
                                       RedirectAttributes attributes) {
        ErrorResponse error = ErrorResponse.of(ErrorCode.REQUEST_VALIDATION_FAIL, e.getBindingResult());
        String viewName = viewName(request.getServletPath());
        RedirectView response = new RedirectView(viewName, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(String pathInfo) {
        switch (pathInfo) {
            case "/users":
                return "/users/forms/create";
            case "/users/me/questions":
                return "/users/me/questions";
            default:
                return "/";
        }
    }
}
