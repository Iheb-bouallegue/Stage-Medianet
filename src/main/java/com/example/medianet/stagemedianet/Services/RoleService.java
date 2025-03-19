package com.example.medianet.stagemedianet.Services;


import com.example.medianet.stagemedianet.entity.Permission;
import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.repository.PermissionRepository;
import com.example.medianet.stagemedianet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

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
    public Role addPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<>());
        }
        role.getPermissions().add(permission);

        return roleRepository.save(role);
    }
}
