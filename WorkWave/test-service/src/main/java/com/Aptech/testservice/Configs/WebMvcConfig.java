package com.Aptech.testservice.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TesstHeaderInterceptor tesstHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tesstHeaderInterceptor)
                .addPathPatterns("/testcases/**"); // các route cần project id
    }
}
