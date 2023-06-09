package com.scaler.blogapi.users;

import com.scaler.blogapi.security.authtokens.AuthTokenRepository;
import com.scaler.blogapi.security.authtokens.AuthTokenService;
import com.scaler.blogapi.security.jwt.JWTService;
import com.scaler.blogapi.users.dto.CreateUserDTO;
import com.scaler.blogapi.users.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersServiceTests {
    @Autowired
    private UsersRepository usersRepository;
    private UsersService usersService;
    private AuthTokenRepository authTokenRepository;

    private UsersService getUsersService() {
        if (usersService == null) {
            ModelMapper modelMapper = new ModelMapper();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            JWTService jwtService = new JWTService();
            AuthTokenService authTokenService = new AuthTokenService(authTokenRepository);
            usersService = new UsersService(
                    usersRepository,
                    modelMapper,
                    passwordEncoder,
                    jwtService,
                    authTokenService
            );
        }
        return usersService;
    }


    @Test
    public void testCreateUser() {
        var newUserDTO = new CreateUserDTO();
        newUserDTO.setEmail("saielk@gmail.com");
        newUserDTO.setPassword("password");
        newUserDTO.setUsername("saiel123");
        UserResponseDTO savedUser = getUsersService().createUser(newUserDTO);
        assertNotNull(savedUser);
    }
}
