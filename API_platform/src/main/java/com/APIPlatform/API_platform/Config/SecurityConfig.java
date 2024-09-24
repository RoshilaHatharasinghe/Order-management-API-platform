package com.APIPlatform.API_platform.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain that applies to incoming HTTP requests.
     * Configures the security settings for the application.
     *
     * @param http the HttpSecurity object to configure security details.
     * @return SecurityFilterChain for securing the web application.
     * @throws Exception if any configuration error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configure Cross-Site Request Forgery (CSRF) protection
                .csrf(csrf -> csrf
                        // Ignore CSRF protection for specific endpoints (user signup and login)
                        .ignoringRequestMatchers("api/v1/user/signup", "api/v1/user/login")
                )
                // Configure access control for different endpoints
                .authorizeHttpRequests(authorize -> authorize
                        // Allow unauthenticated access to signup and login APIs
                        .requestMatchers("api/v1/user/signup", "api/v1/user/login").permitAll()
                        // Secure all other endpoints
                        .anyRequest().authenticated()
                );
        // Build and return the configured SecurityFilterChain.
        return http.build();
    }
}