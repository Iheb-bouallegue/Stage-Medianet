package com.example.medianet.stagemedianet.Services;

import com.example.medianet.stagemedianet.entity.User;
import com.example.medianet.stagemedianet.reposotory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service


public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Récupère le nom du rôle de l'utilisateur (assurez-vous que le rôle est bien un objet 'Role' et que son nom est accessible)
        String roleName = user.getRole().getName();  // Assurez-vous que 'getRole' renvoie un objet 'Role' et non une chaîne

        // Crée une autorité basée sur le nom du rôle (assurez-vous que 'roleName' est bien une chaîne de caractères)
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleName)));
    }
}

