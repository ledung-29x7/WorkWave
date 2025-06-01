package com.Aptech.projectservice.Configs;

import org.springframework.stereotype.Component;

@Component
public class ProjectContext {
    private static final ThreadLocal<String> projectIdHolder = new ThreadLocal<>();

    public static void setProjectId(String projectId) {
        projectIdHolder.set(projectId);
    }

    public static String getProjectId() {
        return projectIdHolder.get();
    }

    public static void clear() {
        projectIdHolder.remove();
    }
}
