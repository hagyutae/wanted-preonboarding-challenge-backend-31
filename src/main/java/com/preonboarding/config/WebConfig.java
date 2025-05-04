package com.preonboarding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.service.user.UserService;
import com.preonboarding.util.UserIdFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<UserIdFilter> userIdFilterRegistration(UserService userService, ObjectMapper objectMapper) {
        FilterRegistrationBean<UserIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserIdFilter(userService, objectMapper));
        registrationBean.addUrlPatterns("/api/products/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
