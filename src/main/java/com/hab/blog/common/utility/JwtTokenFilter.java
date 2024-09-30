package com.hab.blog.common.utility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtAuthenticationProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    private String resolveToken(HttpServletRequest req) {
            String bearerToken = req.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
            return null;
        }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        try {
            if (token != null && jwtAuthenticationProvider.validateToken(token)) {
                Authentication auth = jwtAuthenticationProvider.getAuthentication(token);
                if (auth != null) {
                    logger.info("User: " + auth.getName() + ", Authorities: " + auth.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            // 更详细地记录或处理JWT异常
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is invalid");
            return; // 注意：在发送错误响应后，不要调用filterChain.doFilter
        }
        filterChain.doFilter(request, response);
    }
}
