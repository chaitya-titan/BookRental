package com.crio.bookrental.dtos;

import lombok.Data;

@Data
public class LoginUserDTO {
    private String email;
    private String password;
}
