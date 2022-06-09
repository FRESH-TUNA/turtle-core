package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorResponse;
import com.remember.core.exceptions.RememberAuthenticationException;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class RememberAuthenticatedExceptionRedirectSupport {
    public static RedirectView process(HttpServletRequest request,
                                       RememberAuthenticationException e,
                                       RedirectAttributes attributes) {

        ErrorResponse error = ErrorResponse.of(e.getErrorCode());
        String viewName = viewName(request.getServletPath());
        RedirectView response = new RedirectView(viewName, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(String pathInfo) {
        switch (pathInfo) {
            case "/users":
                return "/users/forms/create";
            default:
                return "/";
        }
    }
}
