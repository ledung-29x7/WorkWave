package com.Aptech.projectservice.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ProjectHeaderInterceptor projectHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectHeaderInterceptor)
                .addPathPatterns("/epic/**", "/sprint/**", "/stories/**", "/tasks/**"); // các route cần project id
    }
}
