package com.APIPlatform.API_platform.Config;

import com.APIPlatform.API_platform.Repository.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final UserRepo userRepository;

    // Constructor-based dependency injection of the UserRepo
    public ApplicationConfig(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Provides a UserDetailsService bean.
     * Used by Spring Security to retrieve user details from the database via email.
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Provides a BCryptPasswordEncoder bean.
     * This is used to securely hash passwords using the BCrypt algorithm.
     * @return BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean.
     * This is a central interface for Spring Security's authentication process.
     * @param config The AuthenticationConfiguration, auto-configured by Spring Security.
     * @return AuthenticationManager
     * @throws Exception if authentication manager could not be provided
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides an AuthenticationProvider bean.
     * This is responsible for authenticating a user against the database using
     * the user details and password encoder. It uses the DaoAuthenticationProvider.
     * @return AuthenticationProvider
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}