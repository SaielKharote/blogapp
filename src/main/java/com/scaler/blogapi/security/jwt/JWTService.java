package com.scaler.blogapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private Algorithm algorithm = Algorithm.HMAC256("SECRET SIGNING KEY");

    public String createJWT(Integer id) {
        return createJWT(
                id,
                new Date(),
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)
        );
    }

    protected String createJWT(Integer id, Date iat, Date exp) {
        String token = JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(iat)
                .withExpiresAt(exp)
                .sign(algorithm);
        return token;
    }

    public Integer getUserIdFromJWT(String jwt) {
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(jwt);
        var subject = decodedJWT.getSubject();
        return Integer.parseInt(subject);
    }
}
