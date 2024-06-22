package com.crio.bookrental.controllers;

import com.crio.bookrental.dtos.LoginResponseDTO;
import com.crio.bookrental.dtos.LoginUserDTO;
import com.crio.bookrental.dtos.RegisterUserDTO;
import com.crio.bookrental.dtos.UserResponseDTO;
import com.crio.bookrental.exceptions.IncorrectPassword;
import com.crio.bookrental.exceptions.UserAlreadyExistsException;
import com.crio.bookrental.exceptions.UserNotFoundException;
import com.crio.bookrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserDTO registerUserDTO)
            throws UserAlreadyExistsException {
        UserResponseDTO userResponseDTO = userService.registerUser(registerUserDTO);
        return ResponseEntity.created(null).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginUserDTO loginUserDTO)
            throws UserNotFoundException, IncorrectPassword {
        LoginResponseDTO loginResponseDTO = userService.loginUser(loginUserDTO);
        return ResponseEntity.ok().body(loginResponseDTO);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody RegisterUserDTO registerUserDTO)
            throws UserAlreadyExistsException{
        UserResponseDTO userResponseDTO = userService.registerAdmin(registerUserDTO);
        return ResponseEntity.created(null).body(userResponseDTO);
    }

}
