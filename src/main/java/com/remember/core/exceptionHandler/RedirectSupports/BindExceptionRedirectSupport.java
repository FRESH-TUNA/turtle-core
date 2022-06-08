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
        System.out.println(error.getErrors().get(0).getReason());
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(String pathInfo) {
        switch (pathInfo) {
            case "/auth/signup":
                return "/auth/forms/signup";
            default:
                return "/";
        }
    }
}
