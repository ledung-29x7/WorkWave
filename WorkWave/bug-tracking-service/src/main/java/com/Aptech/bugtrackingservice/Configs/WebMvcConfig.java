package com.Aptech.bugtrackingservice.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final BugHeaderInterceptorceptor bugHeaderInterceptorceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bugHeaderInterceptorceptor)
                .addPathPatterns("/bug/**"); // các route cần project id
    }
}
