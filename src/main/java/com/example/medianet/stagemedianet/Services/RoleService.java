package com.example.medianet.stagemedianet.Services;


import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.reposotory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void addRole(String roleName) {
        // Vérifier si le rôle existe déjà
        if (roleRepository.findByName(roleName) == null) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            System.out.println("Role " + roleName + " added successfully!");
        } else {
            System.out.println("Role " + roleName + " already exists.");
        }
    }
}
