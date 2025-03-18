package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.reposotory.RoleRepository;
import com.example.medianet.stagemedianet.reposotory.UserRepository;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j

public class RegistrationLoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");

        }
        // Vérifier si le rôle existe
        String roleName = user.getRole().getName();  // Récupère le rôle depuis l'utilisateur
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            return ResponseEntity.badRequest().body("Invalid role specified");
        }

        // Assigner le rôle à l'utilisateur
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return ResponseEntity.ok("login successful");
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        }

    }

