package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.ErrorResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class BindExceptionRedirectSupport {
    private static String USERS_ME_QUESTIONS_ID = "/users/me/questions/([^\\/?\\s]+)$";
    private static String FORMS_UPDATE = "/forms/update";

    public static RedirectView process(HttpServletRequest request,
                                       BindException e,
                                       RedirectAttributes attributes) {
        ErrorResponse error = ErrorResponse.of(ErrorCode.REQUEST_VALIDATION_FAIL, e.getBindingResult());
        String viewName = viewName(request);
        RedirectView response = new RedirectView(viewName, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(HttpServletRequest request) {
        String pathInfo = request.getServletPath();
        switch (pathInfo) {
            case "/auth/signup":
                return "/auth/forms/signup";
            case "/users/me/questions":
                return "/users/me/questions";
        }

        if (checkUsersMeQuestionsIdUpdateRequest(request))
            return pathInfo + FORMS_UPDATE;

        //리다이렉션이 동일한 경우 동일하게 처리한다.
        return pathInfo;
    }

    private static boolean checkUsersMeQuestionsIdUpdateRequest(HttpServletRequest request) {
        return request.getServletPath().matches(USERS_ME_QUESTIONS_ID)
                && request.getMethod().equals("PUT");
    }
}
