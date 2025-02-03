package com.qltv.QLTV.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qltv.QLTV.DTO.Response.ApiResponse;
import com.qltv.QLTV.Exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        // Set status
        response.setStatus(errorCode.getHttpStatus().value());
        // Đổi thành JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Set body cho exception
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        //Đổi từ ApiRes thành String
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
