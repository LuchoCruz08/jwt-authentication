package com.jwtauth.Service;
import com.jwtauth.DTO.LoginUserDTO;
import com.jwtauth.DTO.RegisterUserDTO;
import com.jwtauth.Entity.User;
import com.jwtauth.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepository, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDTO register) {
        User user = new User();
        user.setFullName(register.getFullName());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO login) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

        return userRepository.findByEmail(login.getEmail()).orElseThrow();
    }

}
