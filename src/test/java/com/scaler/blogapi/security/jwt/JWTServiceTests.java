package com.scaler.blogapi.security.jwt;

import com.scaler.blogapi.security.jwt.JWTService;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JWTServiceTests {

    private JWTService jwtService = new JWTService();

    @Test
    void canCreateJWTFromUserId() {
        Integer id = 1133;
        String jwt = jwtService.createJWT(id, new Date(1684733372), new Date(1684128572));
        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTMzIiwiZXhwIjoxNjg0MTI4LCJpYXQiOjE2ODQ3MzN9.TvNLQOSBOqYqIdq-emXrY3xXbjoL_xndnJ5qQqtkNMg", jwt);
    }

    @Test
    void canVerifyJWT() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTMzIiwiZXhwIjoxNjg0NzMzMzcyLCJpYXQiOjE2ODQxMjg1NzJ9.8x9XB55LKhelz0I6RlDtnbB1ma-QoOSFEUCY4JTue1I";
        Integer id = jwtService.getUserIdFromJWT(jwt);
        assertEquals(1133, id);
    }


}
