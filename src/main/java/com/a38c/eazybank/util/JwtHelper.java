package com.a38c.eazybank.util;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

import com.a38c.eazybank.constants.SecurityConstants;
import com.a38c.eazybank.model.User;
import com.a38c.eazybank.services.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtHelper {

    private static final long TOKEN_VALIDITY_IN_MILLIS = 5000L;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public JwtHelper() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(SecurityConstants.JWT_KEY);
        }
        if (verifier == null) {
            verifier = JWT.require(algorithm)
            .withIssuer(SecurityConstants.ISSUER)
            .build();
        }
    }

    public String createJWT(UserDetailsImpl user) {
        List<String> roles = new ArrayList<String>();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        String jwtToken = JWT.create()
          .withIssuer(SecurityConstants.ISSUER)
          .withSubject(user.getEmail())
          .withClaim("userId", user.getUserId())
          .withClaim("roles", roles)
          .withIssuedAt(new Date(System.currentTimeMillis()))
          .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
          .withJWTId(UUID.randomUUID().toString())
          .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT verifyJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public DecodedJWT decodedJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT;
        } catch (JWTDecodeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User parseUser(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        String userId = decodedJWT.getClaim("userId").asString();
        String subject = decodedJWT.getSubject();
        User u = new User();
        u.setEmail(subject);
        u.setUserId(userId);
        u.setRole(roles[0]);
        return u;
    }

    public String getClaim(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        return claim != null ? claim.asString() : null;
    }

    public boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    public void main(String args[]) throws InterruptedException {
        new JwtHelper();

        User user = new User();
        user.setEmail("bigjwt@example.com");
        user.setId(1L);
        user.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        user.setUpdatedAt(null);
        user.setRole("USER");
        user.setFirstName("George");
        user.setLastName("Morose");
        user.setPassword("123465");
        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid.toString());
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        String jwtToken = createJWT(userDetails);
        System.out.println("Created JWT : " + jwtToken);

        Thread.sleep(1000L);

        DecodedJWT decodedJWT = verifyJWT(jwtToken);
        if (decodedJWT == null) {
            System.out.println("JWT Verification Failed");
        }

        decodedJWT = decodedJWT(jwtToken);
        if (decodedJWT != null) {
            System.out.println("Token Issued At : " + decodedJWT.getIssuedAt());
            System.out.println("Token Expires At : " + decodedJWT.getExpiresAt());

            Boolean isExpired = isJWTExpired(decodedJWT);
            System.out.println("Is Expired : " + isExpired);
        }
    }
}
