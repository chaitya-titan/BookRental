package com.crio.bookrental.services;

import com.crio.bookrental.config.jwt.JWTService;
import com.crio.bookrental.dtos.LoginResponseDTO;
import com.crio.bookrental.dtos.LoginUserDTO;
import com.crio.bookrental.dtos.RegisterUserDTO;
import com.crio.bookrental.dtos.UserResponseDTO;
import com.crio.bookrental.exceptions.IncorrectPassword;
import com.crio.bookrental.exceptions.UserAlreadyExistsException;
import com.crio.bookrental.exceptions.UserNotFoundException;
import com.crio.bookrental.models.Role;
import com.crio.bookrental.models.User;
import com.crio.bookrental.repositories.UserRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JWTService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO registerUser(@NonNull RegisterUserDTO registerUserDTO) {
        if(userRepository.findByEmail(registerUserDTO.getEmail()) != null){
            throw new UserAlreadyExistsException("User already exists");
        }
        registerUserDTO.setCreatedDate(LocalDate.now());
        User user = modelMapper.map(registerUserDTO, User.class);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        logger.info("User {} registered successfully", user.getEmail());
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO registerAdmin(RegisterUserDTO registerUserDTO) {
        if(userRepository.findByEmail(registerUserDTO.getEmail()) != null){
            throw new UserAlreadyExistsException("User already exists");
        }
        registerUserDTO.setCreatedDate(LocalDate.now());
        User user = modelMapper.map(registerUserDTO, User.class);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        User savedUser = userRepository.save(user);
        logger.info("Admin {} registered successfully", user.getEmail());
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public LoginResponseDTO loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.getEmail());
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        if(!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())){
            throw new IncorrectPassword("Incorrect password");
        }
        LoginResponseDTO loginResponseDTO = modelMapper.map(user, LoginResponseDTO.class);
        loginResponseDTO.setToken(jwtService.generateToken(user.getId()));
        logger.info("User {} logged in successfully", user.getEmail());
        logger.info("User is a {}", user.getRole());
        return loginResponseDTO;
    }
}
