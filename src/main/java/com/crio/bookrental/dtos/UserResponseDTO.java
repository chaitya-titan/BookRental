package com.crio.bookrental.dtos;

import com.crio.bookrental.models.Role;
import lombok.Data;

@Data
public class UserResponseDTO {
    public Long id;
    public Role role;
}
