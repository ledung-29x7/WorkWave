package com.Aptech.userservice.Configs;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ProjectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String projectId = request.getHeader("X-Project-Id");

        if (projectId == null || projectId.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"status\":\"FAILURE\", \"message\":\"Missing X-Project-Id header\"}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        ProjectContext.setProjectId(projectId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        ProjectContext.clear();
    }
}
