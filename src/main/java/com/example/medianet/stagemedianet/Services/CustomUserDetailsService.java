package com.example.medianet.stagemedianet.Services;


import com.example.medianet.stagemedianet.entity.Permission;
import com.example.medianet.stagemedianet.entity.Role;
import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

@Service


public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Récupérer l'utilisateur à partir de l'Optional
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));


        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Récupérer le rôle de l'utilisateur
        Role role = user.getRole();

        // Récupérer les permissions du rôle
        Set<Permission> permissions = role.getPermissions();

        // Créer une liste d'autorités basée sur les permissions
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getNom()));
        }

        // Retourner l'utilisateur avec ses autorités
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

