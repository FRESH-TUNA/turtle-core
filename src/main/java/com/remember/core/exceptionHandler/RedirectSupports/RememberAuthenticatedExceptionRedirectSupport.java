package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorResponse;
import com.remember.core.exceptions.RememberAuthenticationException;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class RememberAuthenticatedExceptionRedirectSupport {
    private static final String AUTH_FORMS_SIGNIN = "/auth/forms/login";

    public static RedirectView process(HttpServletRequest request,
                                       RememberAuthenticationException e,
                                       RedirectAttributes attributes) {

        ErrorResponse error = ErrorResponse.of(e.getErrorCode());
        RedirectView response = new RedirectView(AUTH_FORMS_SIGNIN, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }
}
