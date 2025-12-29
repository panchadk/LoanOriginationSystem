package com.adminplatform.los_auth.authorize.config;

import com.adminplatform.los_auth.authorize.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // ✅ TEMPORARY: allow all requests
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
*/


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())



                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/party").permitAll() // ✅ ADD THIS
                        .requestMatchers("/api/party").permitAll()                  // ✅ ADD THIS
                        .requestMatchers("/api/party/**").permitAll()
                        .requestMatchers("/api/deal/**").permitAll()
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/tenants",
                                "/api/roles",
                                "/api/test/**",
                                "/api/party/**",
                                "/api/party",
                                // ✅ Allow unauthenticated access to Party endpoints
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",
                                "/",
                                "/index.html",
                                "/manifest.json",
                                "/favicon.ico",
                                "/static/**"
                        ).permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )


                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/manifest.json",
                "/favicon.ico",
                "/static/**"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "https://4da544318f60.ngrok-free.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ SecurityConfig loaded and filter registered");
    }
}
