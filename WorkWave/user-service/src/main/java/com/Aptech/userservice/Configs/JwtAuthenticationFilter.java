package com.Aptech.userservice.Configs;

import java.io.IOException;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Aptech.userservice.Repositorys.UserRepository;
import com.Aptech.userservice.Services.Implement.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private static final Pattern UUID_PATTERN = Pattern.compile("^/[0-9a-fA-F\\-]{36}$");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null || !tokenProvider.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (tokenProvider.isTokenBlacklisted(token)) {
            System.out.println("🚫 Token is blacklisted");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"FAILURE\", \"message\":\"Token is blacklisted\"}");
            return;
        }

        String userId = null;
        try {
            userId = tokenProvider.getUserIdFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("Request path: " + path);

        boolean isProjectIndependent = path.startsWith("/auth") ||
                path.startsWith("/permissions") ||
                path.startsWith("/roles") ||
                path.startsWith("/customer") ||
                path.startsWith("/user") ||
                path.startsWith("/users") || // 👈 cái này sẽ match tất cả /users/*
                path.contains("/users/user") || // ✅ Bổ sung check cụ thể nếu cần
                UUID_PATTERN.matcher(path).matches() ||
                (path.equals("/projects") && method.equals("POST")) ||
                (path.equals("/projects") && method.equals("GET"));
        System.out.println("isProjectIndependent = " + isProjectIndependent);

        try {
            UserDetails userDetails;

            if (isProjectIndependent) {
                userDetails = userDetailsService.loadUserGlobalAuthorities(userId);
            } else {
                String projectId = request.getHeader("X-Project-Id");
                if (projectId == null || projectId.isBlank()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"status\":\"FAILURE\", \"message\":\"Missing X-Project-Id header\"}");
                    return;
                }

                userDetails = userDetailsService.loadUserByIdAndProject(userId, projectId);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ✅ Set full SecurityContext (sửa chỗ này)
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            System.out.println("✅ Granted Authorities: " + authentication.getAuthorities());

        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found or not assigned to project.");
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Authentication error: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

}