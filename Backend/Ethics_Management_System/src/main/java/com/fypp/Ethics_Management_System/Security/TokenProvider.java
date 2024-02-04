package com.fypp.Ethics_Management_System.Security;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationMs;

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Remove for production
        //String encodedSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
       // logger.debug("Using secret for token generation: {}", encodedSecret);

        String token = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();

        logger.info("Generated JWT token for user: {} with expiration: {}", userPrincipal.getUsername(), new Date(new Date().getTime() + jwtExpirationMs));
        return token;

    }

    public boolean validateToken(String token) {
        try {

            // Remove for production
            //String encodedSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
            //logger.debug("Using encoded secret for token validation: {}", encodedSecret);

            Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);


            logger.info("Token validated successfully");
            return true;
                }   catch (SignatureException ex) {
                logger.error("Invalid JWT signature", ex);
            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT token", ex);
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT token", ex);
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT token", ex);
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims string is empty.", ex);
            }
            return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
            //Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

            String username = claims.getSubject();
            logger.debug("Extracted Claims - Username: {}, Expiry: {}", claims.getSubject(), claims.getExpiration());
            logger.debug("Processing JWT: {}", token);
            logger.debug("Signature Algorithm: {}", Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getHeader().getAlgorithm());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                logger.info("UserDetails loaded - Username: {}, Authorities: {}", userDetails.getUsername(), userDetails.getAuthorities());
            } else {
                logger.warn("UserDetails not found for username: {}", username);
            }

            logger.info("Authenticated user: {} with roles: {}", username, userDetails.getAuthorities());
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        } catch (Exception e) {
            logger.error("Authentication error A - Exception type: {}, Message: {}", e.getClass().getSimpleName(), e.getMessage());

            logger.error("Authentication error B: {}", e.getMessage());
            return null;
        }
    }

}