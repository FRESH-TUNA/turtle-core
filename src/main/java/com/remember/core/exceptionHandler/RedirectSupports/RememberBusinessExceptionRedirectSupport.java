package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorResponse;
import com.remember.core.exceptions.RememberAuthenticationException;
import com.remember.core.exceptions.RememberBusinessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class RememberBusinessExceptionRedirectSupport {
    private static String USERS_ME_QUESTIONS = "/users/me/questions";
    private static String USERS_ME_QUESTIONS_ID = "/users/me/questions/([^\\/?\\s]+)$";
    public static RedirectView process(HttpServletRequest request,
                                       RememberBusinessException e,
                                       RedirectAttributes attributes) {

        ErrorResponse error = ErrorResponse.of(e.getErrorCode());
        String viewName = viewName(request);
        RedirectView response = new RedirectView(viewName, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(HttpServletRequest request) {
        if (checkUsersMeQuestionsIdRequest(request))
            return USERS_ME_QUESTIONS;

        //리다이렉션이 동일한 경우 동일하게 처리한다.
        return "/";
    }

    private static boolean checkUsersMeQuestionsIdRequest(HttpServletRequest request) {
        return request.getServletPath().matches(USERS_ME_QUESTIONS_ID);
    }
}
