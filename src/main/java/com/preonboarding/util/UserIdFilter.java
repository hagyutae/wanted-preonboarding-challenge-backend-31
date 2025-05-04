package com.preonboarding.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserIdFilter extends OncePerRequestFilter {
    private final UserService userService;

    public UserIdFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Long userId = JwtUtil.getUserId();
            userService.checkUserExists(userId);

            filterChain.doFilter(request, response);
        } catch (BaseException ex) {
            setExceptionResponse(response,ex);
        }
    }

    private void setExceptionResponse(HttpServletResponse response, BaseException ex) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(ex));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
