package com.project.dine.reserve.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dine.reserve.dto.common.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, BaseResponse.unAuthorized(ex.getMessage()));
        } catch (AccountExpiredException e) {
            setErrorResponse(response, HttpStatus.PRECONDITION_FAILED, BaseResponse.tokenExpired(e.getMessage()));
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus httpStatus, BaseResponse<String> baseResponse) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
    }
}
