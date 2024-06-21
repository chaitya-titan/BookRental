package com.crio.bookrental.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel{
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
