package com.angebhd.studentManagement.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.angebhd.studentManagement.service.JWTUtilities;
import com.angebhd.studentManagement.service.UsersDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtilities jwtUtilities;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String authenticationHeader = req.getHeader("Authorization");
        String token = null;
        String username = null;


        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ") && authenticationHeader.length() > 8) {
            token = authenticationHeader.substring(7);
            username = jwtUtilities.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = applicationContext.getBean(UsersDetailsService.class)
                    .loadUserByUsername(username);

            if (jwtUtilities.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(req, res);
    }
}