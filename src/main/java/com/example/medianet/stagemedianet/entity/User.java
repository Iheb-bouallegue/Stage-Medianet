package com.example.medianet.stagemedianet.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatic ID generation
    private Long id;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;  // Le rôle de l'utilisateur
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private User manager;
    // Les subordonnés de ce manager
    @OneToMany(mappedBy = "manager")
    @JsonManagedReference
    private List<User> teamMembers;

    public User() {
    }

    // User.java
    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    public User(Long id, String username, String email, String password, Role role, boolean active, List<User> teamMembers, String resetToken, LocalDateTime tokenExpiry, User manager) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
        this.teamMembers = teamMembers;
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
        this.manager = manager;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public List<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<User> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Username cannot be null") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(message = "Username cannot be null") String username) {
        this.username = username;
    }

    public @NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public @NotNull(message = "Password cannot be null") @Size(min = 6, message = "Password must be at least 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password cannot be null") @Size(min = 6, message = "Password must be at least 6 characters") String password) {
        this.password = password;
    }





    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +

                '}';
    }
}
