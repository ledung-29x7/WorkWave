package com.Aptech.userservice.Configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ProjectInterceptor projectInterceptor;

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(projectInterceptor)
    // .addPathPatterns("/members/**"); // các route cần project id
    // }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectInterceptor)
                .addPathPatterns("/**") // Áp dụng toàn cục
                .excludePathPatterns("/auth/**", "/projects") // loại trừ route không cần projectId
                .order(Ordered.HIGHEST_PRECEDENCE); // đảm bảo chạy sớm
    }
}
