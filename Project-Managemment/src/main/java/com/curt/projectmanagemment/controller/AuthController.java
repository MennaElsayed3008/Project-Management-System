package com.curt.projectmanagemment.controller;

import com.curt.projectmanagemment.dto.SignUp;
import com.curt.projectmanagemment.dto.Login;
import com.curt.projectmanagemment.security.Role;
import com.curt.projectmanagemment.security.User;
import com.curt.projectmanagemment.repository.UserRepository;
import com.curt.projectmanagemment.repository.RoleRepository;

import com.curt.projectmanagemment.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody SignUp request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "❌ Username already exists!";
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return "✅ User registered successfully!";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Login request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (auth.isAuthenticated()) {
            return "✅ Login successful for user: " + request.getUsername();
        } else {
            return "❌ Invalid credentials!";
        }
    }
}
