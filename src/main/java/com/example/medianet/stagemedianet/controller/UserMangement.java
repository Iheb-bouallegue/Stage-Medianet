package com.example.medianet.stagemedianet.controller;

import com.example.medianet.stagemedianet.Services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserMangement {
    @Autowired
    private UserManagementService userService;

    // Endpoint pour supprimer un utilisateur
    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)  // Retourne un statut 204 No Content si l'utilisateur est supprimé
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    // Endpoint pour désactiver un utilisateur
    @PatchMapping("disable/{userId}")
    @ResponseStatus(HttpStatus.OK)  // Retourne un statut 200 OK si l'utilisateur est désactivé
    public void disableUser(@PathVariable Long userId) {
        userService.disableUser(userId);
    }
}
