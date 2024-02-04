package com.fypp.Ethics_Management_System.Security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        try {
            String jwt = getJwtFromRequest(request);
            logger.debug("JWT Received: {}", jwt);

            if (jwt != null && jwt.split("\\.").length == 3) {
                logger.info("JWT structure looks correct.");
            } else {
                logger.warn("JWT structure is incorrect: {}", jwt);
            }

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("JWT token validated and authentication set in security context for request: {}", request.getRequestURL());

            }else{
                logger.info("No JWT token found or token invalid for request: {}", request.getRequestURL());
            }
        } catch (Exception ex) {
            logger.error("Error setting user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}