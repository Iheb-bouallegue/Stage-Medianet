package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.UserProfile;
import com.example.medianet.stagemedianet.repository.UserProfileRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository repository;


    public UserProfile saveProfile(UserProfile profile) {
        return repository.save(profile);
    }


    public Optional<UserProfile> getProfileByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
    public String saveProfileImage(Long userId, MultipartFile file) {
        try {
            // Définir un répertoire pour sauvegarder l'image
            String uploadDir = "/uploads/"; // Utilisez un répertoire approprié

            // Créer un nom de fichier unique (par exemple, avec l'ID de l'utilisateur)
            String filename = userId + "_" + file.getOriginalFilename();

            // Créer un chemin complet
            Path path = Paths.get(uploadDir, filename);

            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Retourner l'URL de l'image sauvegardée
            return "http://yourdomain.com/uploads/" + filename; // Assurez-vous que l'URL soit correcte
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'upload de l'image", e);
        }
    }


}
