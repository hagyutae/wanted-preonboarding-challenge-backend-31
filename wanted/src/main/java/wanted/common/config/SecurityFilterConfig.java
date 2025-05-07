package wanted.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import wanted.common.security.AuthenticationFilter;
import wanted.common.security.JwtExceptionFilter;
import wanted.common.security.TokenProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(userDetailsService, tokenProvider, objectMapper);
    }

    @Bean
    public JwtExceptionFilter exceptionFilter() {
        return new JwtExceptionFilter(objectMapper);
    }
}