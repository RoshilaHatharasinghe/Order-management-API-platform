package com.APIPlatform.API_platform.Config;

import com.APIPlatform.API_platform.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructor to inject dependencies for JWT service, user details service, and exception resolver
    public JwtAuthenticationFilter(
            JWTService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * The core method to filter incoming requests and validate JWT tokens. If the token is valid,
     * it sets the authentication in the security context.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Extract Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If no header is present or the token doesn't start with "Bearer", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = authHeader.substring(7); // "Bearer " is 7 characters long
            final String userEmail = jwtService.extractUsername(jwt);

            // Check if the user is not authenticated already
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // If the token is valid for the given user details, authenticate the user
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // No credentials are provided as authentication is done via JWT
                            userDetails.getAuthorities() // Roles/Authorities of the user
                    );

                    // Set additional details about the request into the authentication object
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set the authenticated user in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // Continue with the remaining filters in the chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // In case of any exceptions during JWT processing, resolve them using the handler
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
