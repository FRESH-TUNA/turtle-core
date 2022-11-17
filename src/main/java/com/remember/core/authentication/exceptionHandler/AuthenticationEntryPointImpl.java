package com.remember.core.authentication.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remember.core.dtos.responses.BaseResponse;
import com.remember.core.exceptions.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorCode errorCode = ErrorCode.NOT_AUTHENTICATION;
        BaseResponse unAuthenticatedResponse = BaseResponse.ofErrorCode(errorCode);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getStatus());

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, unAuthenticatedResponse);
            os.flush();
        }
    }
}
