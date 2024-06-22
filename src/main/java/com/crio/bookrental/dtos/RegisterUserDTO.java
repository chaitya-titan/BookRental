package com.crio.bookrental.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate createdDate;
}
