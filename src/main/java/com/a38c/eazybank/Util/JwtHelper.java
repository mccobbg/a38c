package com.a38c.eazybank.Util;

import java.util.Date;
import java.util.UUID;

import com.a38c.eazybank.constants.SecurityConstants;
import com.a38c.eazybank.model.User;
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
        algorithm = Algorithm.HMAC256(SecurityConstants.JWT_KEY);

        verifier = JWT.require(algorithm)
          .withIssuer(SecurityConstants.ISSUER)
          .build();
    }

    public static String createJWT(String username) {
        String jwtToken = JWT.create()
          .withIssuer(SecurityConstants.ISSUER)
          .withSubject(username)
          //.withClaim(DATA_CLAIM, DATA)
          .withIssuedAt(new Date())
          .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
          .withJWTId(UUID.randomUUID().toString())
          .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
          .sign(algorithm);

          /*
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) { 
				context.getClaims().claims((claims) -> { 
					Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
							.stream()
							.map(c -> c.replaceFirst("^ROLE_", ""))
							.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet)); 
					claims.put("roles", roles); 
				});
			}

            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

            Car car = new Car("yellow", "renault");
            // objectMapper.writeValue(new File("target/car.json"), car);
            String carAsString = objectMapper.writeValueAsString(car);
           */

        return jwtToken;
    }

    public static DecodedJWT verifyJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            return decodedJWT;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static DecodedJWT decodedJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT;
        } catch (JWTDecodeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static User parseUser(DecodedJWT decodedJWT) {

        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        String userId = decodedJWT.getClaim("userId").asString();
        String subject = decodedJWT.getSubject();
        User u = new User();
        u.setEmail(subject);
        u.setUserId(userId);
        u.setRoles(roles);
        return u;
    }

    public static String getClaim(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        return claim != null ? claim.asString() : null;
    }

    public static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    public static void main(String args[]) throws InterruptedException {

        new JwtHelper();

        String jwtToken = createJWT("test@example.com`");
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
