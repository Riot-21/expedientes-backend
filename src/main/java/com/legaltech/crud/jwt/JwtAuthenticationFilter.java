package com.legaltech.crud.jwt;

import java.io.IOException;

import static com.legaltech.crud.config.TokenJWTConfig.*;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String bearerToken = request.getHeader(HEADER_AUTHORIZATION);

            if (bearerToken == null || !bearerToken.startsWith(PREFIX_TOKEN)) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwt = bearerToken.substring(PREFIX_TOKEN.length()); // PREFIX_TOKEN.length()
            Claims claims = jwtService.getTokenClaims(jwt);

            String email = claims.getSubject();
            var userDetails = userService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException ex) {
            SecurityContextHolder.clearContext();
            throw new BadCredentialsException("Token inválido o expirado", ex);
        }

        filterChain.doFilter(request, response);
    }
}
