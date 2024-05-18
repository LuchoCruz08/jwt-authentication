package com.jwtauth.Controller;
import com.jwtauth.DTO.LoginResponse;
import com.jwtauth.DTO.LoginUserDTO;
import com.jwtauth.DTO.RegisterUserDTO;
import com.jwtauth.Entity.User;
import com.jwtauth.Security.Jwt.JwtService;
import com.jwtauth.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO dto) {
        User registeredUser = authService.signup(dto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO dto) {
        User authenticatedUser = authService.authenticate(dto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(response);
    }

}
