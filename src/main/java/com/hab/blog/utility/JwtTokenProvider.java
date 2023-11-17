package com.hab.blog.utility;

import com.hab.blog.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider implements AuthenticationProvider {


    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long validityInMilliseconds = 360000000; // 1h

    @Autowired
    private UserService userService;

    public String createToken(Authentication authentication) {
        String username = authentication.getName();
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userService.loadUserByUsername(getToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private String getToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            boolean isValidateToken = claims.getBody().getExpiration().before(new Date());
            System.out.println("Token Expiration Time: " + claims.getBody().getExpiration());
            System.out.println("Current Time: " + new Date());
            return !isValidateToken;
        } catch (JwtException | IllegalArgumentException e) {
            throw new Exception("Expired or invalid JWT token");
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();

        try {
            if (validateToken(token)) {
                UserDetails userDetails = this.userService.loadUserByUsername(getToken(token));

                return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
            } else {
                throw new BadCredentialsException("Invalid token");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
