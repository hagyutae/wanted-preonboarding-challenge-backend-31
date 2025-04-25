package com.mkhwang.wantedcqrs.config.security;

import com.mkhwang.wantedcqrs.config.endpoint.IsPublicEndpointRequestMatcher;
import com.mkhwang.wantedcqrs.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final IsPublicEndpointRequestMatcher isPublicEndpointRequestMatcher;
  private final UserDetailsService userDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/**")
            )
            .authorizeHttpRequests(
                    (authorize) -> authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                            .permitAll().requestMatchers(this.isPublicEndpointRequestMatcher).permitAll()
                            .requestMatchers("/error").permitAll().anyRequest().authenticated());
    http.formLogin(form -> form.loginPage("/login").permitAll()
            .failureHandler((request, response, exception) -> {
              response.sendRedirect("/issue-token");
            }));

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * 무조건 통과
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
      @Override
      public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(this.userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    ProviderManager providerManager = new ProviderManager(List.of(authProvider));

    return providerManager;
  }
}
