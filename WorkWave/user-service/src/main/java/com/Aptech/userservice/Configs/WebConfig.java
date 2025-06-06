package com.Aptech.userservice.Configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ProjectInterceptor projectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/users/auth/**",
                        "/user/**",
                        "/users/**",
                        "/customer/**",
                        "/users/user/**",
                        "/permissions/**",
                        "/roles/**",
                        "/projects",
                        "/projects/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**")
                .order(Ordered.HIGHEST_PRECEDENCE);
    }

    @PostConstruct
    public void debugInterceptor() {
        System.out.println("✅ Interceptor config loaded with exclude: /users/user/**");
    }

}
