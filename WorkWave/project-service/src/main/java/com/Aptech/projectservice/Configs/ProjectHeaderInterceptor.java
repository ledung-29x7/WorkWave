package com.Aptech.projectservice.Configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class ProjectHeaderInterceptor implements HandlerInterceptor {

    public static final String PROJECT_ID_HEADER = "X-Project-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String projectId = request.getHeader(PROJECT_ID_HEADER);
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // Chỉ check với các method cần Project Id
        if (requiresProjectId(method, uri)) {
            if (projectId == null || projectId.isBlank()) {
                log.warn("Missing X-Project-Id header for path: {}", uri);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing X-Project-Id header");
                return false;
            }
            ProjectContext.setProjectId(projectId);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        // ✅ Luôn clear sau mỗi request để tránh leak dữ liệu
        ProjectContext.clear();
    }

    private boolean requiresProjectId(String method, String uri) {
        // Chỉ định rõ những path cần ProjectId
        return (uri.contains("/epic") ||
                uri.contains("/sprint") ||
                uri.contains("/stories") ||
                uri.contains("/tasks"));
    }
}
