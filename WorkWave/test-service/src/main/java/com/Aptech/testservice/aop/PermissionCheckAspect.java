package com.Aptech.testservice.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.Aptech.testservice.Configs.JwtUtil;
import com.Aptech.testservice.annotation.PermissionRequired;

import org.springframework.http.*;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PermissionCheckAspect {

    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Around("@annotation(com.Aptech.testservice.annotation.PermissionRequired)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        // Lấy token và projectId từ header
        String authHeader = request.getHeader("Authorization");
        String projectId = request.getHeader("X-Project-Id");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        if (projectId == null || projectId.isBlank()) {
            throw new RuntimeException("Missing X-Project-Id header");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token); // ✅ dùng RSA

        // Lấy annotation
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        PermissionRequired annotation = method.getAnnotation(PermissionRequired.class);
        String permissionCode = annotation.value();

        // Gọi sang UserService
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token); // Gửi lại token để xác thực bên UserService

        Map<String, String> body = Map.of(
                "userId", userId,
                "projectId", projectId,
                "permissionCode", permissionCode);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        String url = "http://localhost:8080/users/auth/check-permission"; // ⚠️ cập nhật nếu cần

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map<String, Boolean> result = (Map<String, Boolean>) response.getBody().get("data");

        if (result != null && Boolean.TRUE.equals(result.get("hasPermission"))) {
            return joinPoint.proceed();
        } else {
            throw new RuntimeException("Access Denied: you don't have permission [" + permissionCode + "]");
        }
    }
}
