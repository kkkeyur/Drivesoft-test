package com.drivesoft.testapi.configs.security;

import com.drivesoft.testapi.utils.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        try {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(jwtToken, username)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>()); // Add roles/authorities if necessary
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.sendError(401,"unauthorised");
                }

            }
        }

        chain.doFilter(request, response);

        } catch (Exception e) {
            logger.error( e.getMessage());
        }
    }
}