package com.example.medianet.stagemedianet.controller;


import com.example.medianet.stagemedianet.Configuration.JwtUtils;
import com.example.medianet.stagemedianet.Services.UserProfileService;

import com.example.medianet.stagemedianet.entity.UserProfile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin("*")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/save")
    public ResponseEntity<?> saveProfile(@RequestBody UserProfile profile, HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = Long.valueOf(jwtUtils.extractAllClaims(token).get("id").toString());
        profile.setUserId(userId);

        // Vérifie si un profil existe déjà pour l'utilisateur
        Optional<UserProfile> existingProfile = profileService.getProfileByUserId(userId);
        if (existingProfile.isPresent()) {
            // Si le profil existe déjà, on met à jour ce profil existant
            profile.setId(existingProfile.get().getId());  // On préserve l'ID existant pour la mise à jour
        }

        return ResponseEntity.ok(profileService.saveProfile(profile));  // Sauvegarde ou met à jour le profil
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        String token = extractToken(request);
        Long userId = Long.valueOf(jwtUtils.extractAllClaims(token).get("id").toString());
        return profileService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        } else {
            throw new RuntimeException("Authorization header is missing or invalid");
        }
    }

    @PostMapping("/upload-image/{userId}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun fichier sélectionné.");
            }

            String imageUrl = profileService.saveProfileImage(userId, file);  // Sauvegarde l'image et obtient l'URL
            return ResponseEntity.ok(imageUrl);  // Retourne l'URL de l'image
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'upload de l'image: " + e.getMessage());
        }
    }


}
