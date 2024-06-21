package com.crio.bookrental.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final Long userId;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.split(" ")[1];
        userId = jwtService.getUserIDFromJWT(token);
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            JWTAuthentication jwtAuthentication = new JWTAuthentication(token);
            jwtAuthentication.setUserId(userId);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
        }
        filterChain.doFilter(request, response);
    }
}
