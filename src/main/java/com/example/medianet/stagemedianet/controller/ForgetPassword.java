package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.dto.ForgetPasswordRequest;
import com.example.medianet.stagemedianet.dto.ResetPasswordRequest;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class ForgetPassword {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email introuvable");
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Réinitialisation de mot de passe");
        mail.setText("Cliquez ici pour réinitialiser votre mot de passe : " + resetUrl);
        mailSender.send(mail);

        return ResponseEntity.ok("Lien envoyé par email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken()).orElse(null);

        if (user == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Lien invalide ou expiré"));
        }

        String rawPassword = request.getNewPassword();
        System.out.println("Mot de passe reçu : " + rawPassword);

        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Mot de passe encodé : " + encodedPassword);

        user.setPassword(encodedPassword);
        user.setResetToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);

        System.out.println("Utilisateur sauvegardé avec nouveau mot de passe");

        return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour"));
    }

}
