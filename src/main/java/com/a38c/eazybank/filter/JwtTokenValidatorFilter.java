package com.a38c.eazybank.filter;

import com.a38c.eazybank.Util.JwtHelper;
import com.a38c.eazybank.constants.SecurityConstants;
import com.a38c.eazybank.services.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenValidatorFilter  extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if (null != jwt) {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                try {
                    
                    String authToken = header.substring(7);
                    DecodedJWT decodedJWT = JwtHelper.verifyJWT(authToken);
                    if (decodedJWT != null) {
                        String username = decodedJWT.getSubject();
                        boolean isExpired = JwtHelper.isJWTExpired(decodedJWT);

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
