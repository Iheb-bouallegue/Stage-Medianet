package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Configuration.JwtUtils;
import com.example.medianet.stagemedianet.Services.EmailService;
import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.entity.UserProfile;
import com.example.medianet.stagemedianet.repository.RoleRepository;
import com.example.medianet.stagemedianet.repository.UserProfileRepository;
import com.example.medianet.stagemedianet.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserProfileRepository profileRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(401).body("Email already exists");
        }

        String roleName = user.getRole().getName();
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid role specified");
        }
        user.setRole(role.get());

        if (user.getManager() != null && user.getManager().getId() != null) {
            Optional<User> manager = userRepository.findById(user.getManager().getId());
            if (manager.isEmpty()) {
                return ResponseEntity.badRequest().body("Manager not found");
            }
            user.setManager(manager.get());
        }

        // ✅ Sauvegarder temporairement le mot de passe en clair pour l'e-mail
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        // ✅ Enregistrement de l'utilisateur
        User savedUser = userRepository.save(user);

        // ✅ Envoi de l'e-mail avec le mot de passe en clair
        emailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getUsername(), rawPassword);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Tentative d'authentification avec le username et le password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Si l'authentification réussit
            if (authentication.isAuthenticated()) {
                // Rechercher l'utilisateur dans la base de données avec son username
                User authenticatedUser = userRepository.findByUsername(user.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Vérifier si l'utilisateur est actif
                if (!authenticatedUser.isActive()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Compte utilisateur désactivé. Contactez l'administrateur.");
                }
                // Récupérer le UserProfile à partir de userId
                Optional<UserProfile> profileOpt = profileRepository.findByUserId(authenticatedUser.getId());
                boolean profileCompleted = profileOpt.map(UserProfile::isProfileCompleted).orElse(false);

                // Extraire le rôle de l'utilisateur authentifié
                String roleName = authenticatedUser.getRole().getName();

                // Préparer les données pour générer le token
                Map<String, Object> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(authenticatedUser.getId(), authenticatedUser.getUsername(), roleName, authenticatedUser.getEmail()));
                authData.put("type", "Bearer");
                authData.put("profileCompleted", profileCompleted);



                // Retourner la réponse avec le token
                return ResponseEntity.ok(authData);
            }

            // Si l'authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Optionnel : Tu peux récupérer le token depuis l'en-tête Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Ici tu pourrais ajouter le token dans une blacklist (si tu en gères une)
            System.out.println("Token reçu pour logout : " + token);
        }

        // La réponse est symbolique ici, car le token reste valide jusqu’à son expiration.
        return ResponseEntity.ok("Déconnecté avec succès.");
    }
    @GetMapping("/users/manager/{id}")
    public List<User> getUsersByManager(@PathVariable Long id) {
        return userRepository.findByManager_Id(id);
    }


}

