package com.crio.bookrental.dtos;

import com.crio.bookrental.models.Role;
import lombok.Data;

@Data
public class LoginResponseDTO {
    Long id;
    String email;
    String firstName;
    Role role;
    String token;
}
