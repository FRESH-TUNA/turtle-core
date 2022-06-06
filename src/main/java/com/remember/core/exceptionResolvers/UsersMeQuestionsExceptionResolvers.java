//package com.remember.core.exceptionResolvers;
//
//import com.remember.core.exceptions.users.UsersMeQuestionsEntityNotFoundException;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//// https://bcp0109.tistory.com/303
//
//@ControllerAdvice
//public class UsersMeQuestionsExceptionResolvers {
//    @ExceptionHandler
//    public ModelAndView handleError(UsersMeQuestionsEntityNotFoundException e) {
//        // exception 결과 표시용 객체 처리
//        return new ModelAndView("redirect:/users/me/questions");
//    }
//}
