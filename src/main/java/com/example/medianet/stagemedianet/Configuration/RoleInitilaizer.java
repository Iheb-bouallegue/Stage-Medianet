package com.example.medianet.stagemedianet.Configuration;

import com.example.medianet.stagemedianet.Services.RoleService;
import com.example.medianet.stagemedianet.entity.Permission;
import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.repository.PermissionRepository;
import com.example.medianet.stagemedianet.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RoleInitilaizer {
    @Autowired
    private RoleService roleService;

   // @PostConstruct
    //public void init() {
        // Ajouter des rôles au démarrage
      // roleService.addRole("ROLE_RH");
       // roleService.addRole("ROLE_ADMIN");
       // roleService.addRole("ROLE_EMPLOYEE");
       // roleService.addRole("ROLE_MANAGER");
   // }
   @Bean
   CommandLineRunner initData(RoleRepository roleRepository, PermissionRepository permissionRepository) {
       return args -> {
           // Création des permissions
           List<String> permissionsList = List.of(
                   "CAN_CREATE_USER", "CAN_EDIT_USER", "CAN_DELETE_USER", "CAN_VIEW_USERS",
                   "CAN_CREATE_TRAINING", "CAN_EDIT_TRAINING", "CAN_DELETE_TRAINING", "CAN_VIEW_TRAININGS",
                   "CAN_CREATE_GOAL", "CAN_EDIT_GOAL", "CAN_DELETE_GOAL", "CAN_VIEW_GOALS",
                   "CAN_CREATE_EVALUATION", "CAN_VIEW_EVALUATIONS",
                   "CAN_MANAGE_EMPLOYEES", "CAN_ACCESS_EMPLOYEE_DATA",
                   "CAN_DELETE_EMPLOYEE", "CAN_DISABLE_EMPLOYEE"

           );

           List<Permission> permissions = permissionsList.stream()
                   .map(name -> permissionRepository.findByNom(name).orElseGet(() -> {
                       Permission permission = new Permission();
                       permission.setNom(name);
                       return permissionRepository.save(permission);
                   }))
                   .toList();

           // Attribution des permissions aux rôles
           Map<String, List<String>> rolePermissions = Map.of(
                   "ADMIN", permissionsList,
                   "USER", List.of("CAN_VIEW_TRAININGS", "CAN_VIEW_GOALS", "CAN_VIEW_EVALUATIONS"),
                   "MANAGER", List.of("CAN_CREATE_GOAL", "CAN_EDIT_GOAL", "CAN_VIEW_GOALS", "CAN_VIEW_TRAININGS", "CAN_CREATE_EVALUATION"),
                   "RH", List.of("CAN_MANAGE_EMPLOYEES", "CAN_ACCESS_EMPLOYEE_DATA", "CAN_VIEW_USERS", "CAN_VIEW_TRAININGS")
           );

           rolePermissions.forEach((roleName, perms) -> {
               Role role = roleRepository.findByName(roleName)
                       .orElseGet(() -> {
                           Role newRole = new Role();
                           newRole.setName(roleName);
                           return roleRepository.save(newRole);
                       });


               Set<Permission> rolePermissionsSet = permissions.stream()
                       .filter(p -> perms.contains(p.getNom()))
                       .collect(Collectors.toSet());

               role.setPermissions(rolePermissionsSet);
               roleRepository.save(role);
           });

           System.out.println("Rôles et permissions initialisés !");
       };
   }

}
