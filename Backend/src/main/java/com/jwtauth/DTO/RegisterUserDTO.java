package com.jwtauth.DTO;
import lombok.Data;

@Data
public class RegisterUserDTO {

    private String fullName;
    private String email;
    private String password;

}
