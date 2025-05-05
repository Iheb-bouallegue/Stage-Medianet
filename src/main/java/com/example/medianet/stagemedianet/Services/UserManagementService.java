package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour supprimer un utilisateur
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        userRepository.delete(user);
    }

    // Méthode pour désactiver un utilisateur (met à jour un flag 'active')
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setActive(false);  // Suppose que vous avez une propriété 'active' dans l'entité User
        userRepository.save(user);
    }
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(updatedUser.getPassword());
                    existingUser.setRole(updatedUser.getRole());
                    existingUser.setActive(updatedUser.isActive());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
