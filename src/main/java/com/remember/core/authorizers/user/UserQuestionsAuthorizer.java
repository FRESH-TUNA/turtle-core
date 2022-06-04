package com.remember.core.authorizers.user;

import org.springframework.stereotype.Component;

/**
 * 참고자료
 * // https://velog.io/@dhk22/Spring-Security-%ED%86%A0%ED%81%B0%EB%B0%A9%EC%8B%9D-%EC%9D%B8%EC%A6%9D-JWT
 * // https://www.baeldung.com/spring-security-oauth-jwt
 * //https://bcp0109.tistory.com/303
 */
//
//@Component
//public class UserQuestionsAuthorizer {
//    public boolean check(Long userId, Authentication authentication) {
//        Object userDetails = authentication.getPrincipal();
//        if (!(userDetails instanceof RememberUserDetails))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이후 이용하세요");
//
//        if (!(((RememberUserDetails)userDetails).getId().equals(userId)))
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자의 리소스가 아닙니다.");
//    }
//
//    same_re
//}

