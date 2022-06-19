package com.remember.core.exceptionHandler.RedirectSupports;

import com.remember.core.exceptions.ErrorResponse;
import com.remember.core.exceptions.RememberException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public class RememberExceptionRedirectSupport {
    private static String USERS_ME_QUESTIONS = "/users/me/questions";
    private static String USERS_ME_QUESTIONS_ID = "/users/me/questions/([^\\/?\\s]+)$";
    private static String AUTH_SIGNUP = "/auth/signup";
    private static String AUTH_FORM_SIGNUP = "/auth/forms/signup";

    public static RedirectView process(HttpServletRequest request,
                                       RememberException e,
                                       RedirectAttributes attributes) {

        ErrorResponse error = ErrorResponse.of(e.getErrorCode());
        String viewName = viewName(request);
        RedirectView response = new RedirectView(viewName, true);
        attributes.addFlashAttribute("error", error);
        return response;
    }

    private static String viewName(HttpServletRequest request) {
        String path = request.getServletPath();
        if (checkUsersMeQuestionsIdRequest(path))
            return USERS_ME_QUESTIONS;

        if (checkAuthSignupRequest(path))
            return AUTH_FORM_SIGNUP;

        //리다이렉션이 동일한 경우 동일하게 처리한다.
        return "/";
    }

    private static boolean checkUsersMeQuestionsIdRequest(String path) {
        return path.matches(USERS_ME_QUESTIONS_ID);
    }

    private static boolean checkAuthSignupRequest(String path) {
        return path.equals(AUTH_SIGNUP);
    }
}
