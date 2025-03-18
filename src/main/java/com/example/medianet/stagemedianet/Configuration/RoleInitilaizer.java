package com.example.medianet.stagemedianet.Configuration;

import com.example.medianet.stagemedianet.Services.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitilaizer {
    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void init() {
        // Ajouter des rôles au démarrage
        roleService.addRole("ROLE_RH");
        roleService.addRole("ROLE_ADMIN");
        roleService.addRole("ROLE_EMPLOYEE");
        roleService.addRole("ROLE_MANAGER");
    }
}
