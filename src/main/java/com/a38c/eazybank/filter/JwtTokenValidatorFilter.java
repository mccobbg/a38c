package com.a38c.eazybank.filter;

import com.a38c.eazybank.Util.JwtHelper;
import com.a38c.eazybank.constants.SecurityConstants;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.services.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
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
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

//import javax.crypto.SecretKey;
import java.io.IOException;
//import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class JwtTokenValidatorFilter  extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
        if (null != jwt) {
            String header = request.getHeader("Authorization");

            try {
            //     SecretKey key = Keys.hmacShaKeyFor(
            //             SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            //     Claims claims = Jwts.parserBuilder()
            //             .setSigningKey(key)
            //             .build()
            //             .parseClaimsJws(jwt)
            //             .getBody();
            //     String username = String.valueOf(claims.get("username"));
            //     String authorities = (String) claims.get("authorities");
            //     Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
            //             AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            //     SecurityContextHolder.getContext().setAuthentication(auth);
                if (header != null && header.startsWith("Bearer ")) {
                    //throw new JwtTokenMissingException("No JWT token found in request headers");
        
                    String authToken = header.substring(7);

                    DecodedJWT decodedJWT = JwtHelper.verifyJWT(authToken);
                    if (decodedJWT != null) {
                    
                        User user = JwtHelper.parseUser(decodedJWT);
                        String username = user.getEmail();
                        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = userService.loadUserByUsername(username);
                
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
            
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid or missing Token");
            }
        }
        filterChain.doFilter(request, response);
    }

/*

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.extractUsername(jwtToken);
            } catch (Exception e) {
                // Handle token extraction/validation errors
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
// Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userRepo
            .findByUsername(jwtTokenUtil.getUsername(token))
            .orElse(null);

        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
 */

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return request.getServletPath().equals("/user");
    }

}
