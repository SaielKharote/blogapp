package com.scaler.blogapi.users;

import com.scaler.blogapi.users.dto.CreateUserDTO;
import com.scaler.blogapi.users.dto.LoginUserDTO;
import com.scaler.blogapi.users.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsersService(
            UsersRepository usersRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        UserEntity newUserEntity = modelMapper.map(createUserDTO, UserEntity.class);
        newUserEntity.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        UserEntity savedUser = usersRepository.save(newUserEntity);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        return userResponseDTO;
    }

    public UserResponseDTO loginUser(LoginUserDTO loginUserDTO) {
        UserEntity userEntity = usersRepository.findByUsername(loginUserDTO.getUsername());
        if (userEntity == null) {
            throw new IllegalArgumentException(loginUserDTO.getUsername());
        }
        Boolean passMatch = passwordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword());
        if (!passMatch) {
            throw new IllegalArgumentException("Incorrect password");
        }

        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        return userResponseDTO;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Integer id) {
            super("User with id:" + id + " not found");
        }
        public UserNotFoundException(String username) {
            super("User with username:" + username + " not found");
        }
    }

    public static class IncorrectPasswordException extends IllegalArgumentException {
        public IncorrectPasswordException() {
            super("Incorrect password");
        }
    }
}
