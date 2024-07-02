package com.jwtauth.Controller;
import com.jwtauth.DTO.RegisterUserDTO;
import com.jwtauth.Entity.User;
import com.jwtauth.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdmin(@RequestBody RegisterUserDTO dto) {
        User createdAdmin = userService.createAdmin(dto);
        return ResponseEntity.ok(createdAdmin);
    }

}
