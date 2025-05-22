package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.UserManagementService;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserMangement {
    @Autowired
    private UserManagementService userService;
    @Autowired
    private UserRepository userRepository;

    // Endpoint pour supprimer un utilisateur
    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)  // Retourne un statut 204 No Content si l'utilisateur est supprimé
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    // Endpoint pour désactiver un utilisateur
    @PatchMapping("/disable/{userId}")
    @ResponseStatus(HttpStatus.OK)  // Retourne un statut 200 OK si l'utilisateur est désactivé
    public void disableUser(@PathVariable Long userId) {
        userService.disableUser(userId);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/managers")
    public List<User> getAllManagers() {
        return userRepository.findAllManagers();
    }

    @GetMapping("/team/{managerId}")
    public List<User> getTeam(@PathVariable Long managerId) {
        return userRepository.findByManagerId(managerId);
    }




}
