package com.Aptech.releaseservice.Configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ReleaseHeaderInterceptor releaseHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(releaseHeaderInterceptor)
                .addPathPatterns("/epic/**", "/sprint/**", "/stories/**", "/tasks/**"); // các route cần project id
    }
}
