package wanted.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import wanted.common.exception.code.ExceptionCode;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.common.response.ErrorResponse;
import io.jsonwebtoken.JwtException;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handleTokenException(response, GlobalExceptionCode.UNAUTHORIZED);
        }
    }

    private void handleTokenException(HttpServletResponse response, ExceptionCode exceptionCode) throws  IOException {
        ErrorResponse exceptionRes = ErrorResponse.of(exceptionCode.getCode(), exceptionCode.getMessage(), null);
        String message = objectMapper.writeValueAsString(exceptionRes);

        log.error("Error occurred: {}", exceptionCode.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(exceptionCode.getStatus().value());
        response.getWriter().write(message);
    }
}