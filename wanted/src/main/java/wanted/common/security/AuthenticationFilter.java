package wanted.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import wanted.common.exception.code.ExceptionCode;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.common.response.ErrorResponse;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenHeader = request.getHeader("Authorization");

        if (accessTokenHeader == null || !accessTokenHeader.startsWith("Bearer ")) {
            handleExceptionToken(response, GlobalExceptionCode.UNAUTHORIZED);
            return;
        }

        String accessToken = accessTokenHeader.substring("Bearer ".length()).trim();

        try {
            tokenProvider.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            handleExceptionToken(response, GlobalExceptionCode.UNAUTHORIZED);
            return;
        }

        String username = tokenProvider.getUsername(accessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private void handleExceptionToken(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(exceptionCode.getCode(), exceptionCode.getMessage(), null);
        String messageBody = objectMapper.writeValueAsString(errorResponse);

        log.error("Error occurred: {}", exceptionCode.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(exceptionCode.getStatus().value());
        response.getWriter().write(messageBody);
    }
}
