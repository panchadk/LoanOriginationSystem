package com.adminplatform.los_auth.authorize.security;

import com.adminplatform.los_auth.authorize.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/test/public"
    );

    public JwtAuthenticationFilter(JwtService jwtService, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("🔍 Request path: " + path);

        if (isPublicEndpoint(path)) {
            System.out.println("🟢 Public endpoint, skipping auth: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                Claims claims = jwtUtil.extractAllClaims(jwt);
                List<String> roles = claims.get("roles", List.class);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ Authenticated user: " + userEmail);
                System.out.println("🔐 JWT roles: " + roles);
                System.out.println("🔐 Granted authorities: " + authorities);
            } else {
                System.out.println("❌ Token validation failed for user: " + userEmail);
            }
        }

        filterChain.doFilter(request, response);
    }
}
