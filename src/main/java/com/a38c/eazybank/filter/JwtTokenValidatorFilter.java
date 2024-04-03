package com.a38c.eazybank.filter;

import com.a38c.eazybank.constants.SecurityConstants;
import com.a38c.eazybank.services.UserService;
import com.a38c.eazybank.util.JwtHelper;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenValidatorFilter  extends OncePerRequestFilter {

    private final UserService userService;

    public JwtTokenValidatorFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if (null != jwt) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                try {
                    
                    String authToken = header.substring(7);
                    JwtHelper authHelper = new JwtHelper();
                    DecodedJWT decodedJWT = authHelper.verifyJWT(authToken);
                    if (decodedJWT != null) {
                        String username = decodedJWT.getSubject();
                        boolean isExpired = authHelper.isJWTExpired(decodedJWT);

                        if (username != null && !isExpired && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = userService.loadUserByUsername(username);
                
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
            
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } catch (Exception e) {
                    throw new BadCredentialsException("Invalid or missing Token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    // @Override
    // protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
    //     return request.getServletPath().equals("/user");
    // }
}
