package com.example.springfirstlab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final com.example.springfirstlab.JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Admin has access to everything
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        // Course endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").authenticated()
                        .requestMatchers("/api/v1/courses/**").hasAnyRole("ADMIN", "TEACHER")
                        // Enrollment endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/enrollments/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/api/v1/enrollments/**").hasAnyRole("ADMIN", "TEACHER")
                        // Student endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/students/{id}")
                        .access(new WebExpressionAuthorizationManager(
                            "hasRole('ADMIN') or hasRole('TEACHER') or " +
                            "(hasRole('STUDENT') and @userSecurity.isCurrentUser(#id))"
                        ))
                        .requestMatchers("/api/v1/students/**").hasAnyRole("ADMIN", "TEACHER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
