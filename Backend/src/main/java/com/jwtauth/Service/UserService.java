package com.jwtauth.Service;
import com.jwtauth.DTO.RegisterUserDTO;
import com.jwtauth.Entity.Role;
import com.jwtauth.Entity.RoleEnum;
import com.jwtauth.Entity.User;
import com.jwtauth.Repository.RoleRepository;
import com.jwtauth.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User createAdmin(RegisterUserDTO dto) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if(optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

}
