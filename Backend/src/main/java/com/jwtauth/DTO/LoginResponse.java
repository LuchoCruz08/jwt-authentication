package com.jwtauth.DTO;
import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private Long expiresIn;


}
